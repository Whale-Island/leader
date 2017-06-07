package com.leader.game.player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import com.leader.core.db.CommonDao;
import com.leader.core.db.GameEntity;
import com.leader.core.server.model.ShutdownListener;
import com.leader.core.util.TextKit;
import com.leader.game.log.manager.LogManager;
import com.leader.game.player.dao.PlayerDao;
import com.leader.game.player.listener.LogoutListener;
import com.leader.game.player.model.LoginToken;
import com.leader.game.player.model.Player;
import com.leader.game.player.model.RandomName;
import com.leader.game.protobuf.protocol.PlayerProtocol.ResLoginMessage;
import com.leader.game.protobuf.protocol.PlayerProtocol.ResRegisterMessage.Builder;
import com.leader.game.protobuf.protocol.SyncProtocol.ReqVerifyTokenMessage;
import com.leader.game.protobuf.protocol.SyncProtocol.ResVerifyTokenMessage;
import com.leader.game.server.GameServer;
import com.leader.game.server.model.AttributeKeys;
import com.leader.game.server.model.PlayerChannelGroup;
import com.leader.game.server.sync.SyncFutureUtils;
import com.leader.game.util.DateUtils;
import com.leader.game.util.WordFilter;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.util.internal.StringUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayerManager implements ShutdownListener {
	private static class SigletonHolder {
		static final PlayerManager INSTANCE = new PlayerManager();
	}

	public static PlayerManager getInstance() {
		return SigletonHolder.INSTANCE;
	}

	/** 角色名表达式 */
	private static final String regEx = "^[\u4e00-\u9fa5_a-zA-Z0-9]+$";
	private static final Pattern pattern = Pattern.compile(regEx);
	/** 所有玩家 */
	private ConcurrentHashMap<String, Player> roles = new ConcurrentHashMap<String, Player>();
	/** 所有玩家 */
	private ConcurrentHashMap<Long, Player> players = new ConcurrentHashMap<Long, Player>();
	/** 所有角色名 */
	private Set<String> roleNames = Collections.synchronizedSet(new HashSet<String>());
	/** 所有随机名字保存类 */
	private RandomName roleRandom;
	/** 退出游戏Listener */
	private @Getter @Setter List<LogoutListener> logoutListeners;
	/** 登录令牌map */
	private Map<String, LoginToken> tokenMap = new ConcurrentHashMap<String, LoginToken>();

	private static final int NUMBER_LIMIT = 2000;
	@Autowired
	PlayerDao playerDao;
	@Autowired
	CommonDao commonDao;

	/** 离线后token保存时间 */
	private static final int TOKEN_INDATE = 15;

	/** 初始化调用 */
	public void init() {
		List<String> strings = playerDao.loadNickname();
		if (strings != null && !strings.isEmpty()) {
			roleNames.addAll(strings);
		}
		// TODO 初始化随机姓名库
		roleRandom = new RandomName();
	}

	/** 网络连接关闭监听器 */
	private @Getter final ChannelFutureListener futureListener = new ChannelFutureListener() {
		@Override
		public void operationComplete(ChannelFuture future) throws Exception {
			Channel channel = future.channel();
			Player player = channel.attr(AttributeKeys.PLAYER).get();
			if (player != null) {
				logout(player);
			}
			log.info("remote host " + channel.remoteAddress() + " is closed.");
		}
	};

	/**
	 * 注册
	 * 
	 * @param channel2
	 * 
	 * @param nickname
	 * @param icon
	 * @param sex
	 *            性别 1男 2女
	 * @param gameChannel
	 *            游戏渠道
	 * @param deviceId
	 *            设备号
	 * @param response
	 * @return
	 */
	public Player register(Channel channel, String nickname, String icon, int sex, int gameChannel, String deviceId,
			Builder response) {
		try {
			String username = channel.attr(AttributeKeys.USERNAME).get();
			if (StringUtil.isNullOrEmpty(username)) {
				log.info("非法操作！未登录注册！ip-->{}", channel.remoteAddress());
				response.setCode(-1);// 未登录
				return null;
			}

			Player player = playerDao.findPlayerByUsername(username);
			if (player != null) {
				log.info("非法操作！重复注册，帐号-->", username);
				response.setCode(-2);// 已注册
				return null;
			}

			if (!StringUtil.isNullOrEmpty(nickname) && nameIsAvailable(nickname)) {
				response.setCode(1);// 昵称非法或已被占用
				return null;
			}
			player = new Player();
			player.setUsername(username);
			player.setNickname(nickname);
			player.setState(Player.ONLINE);
			player.setIcon(icon);
			player.setSex((short) sex);
			player.setLastOnlineTime(System.currentTimeMillis());
			player.setChannel(gameChannel);
			player.setDeviceId(deviceId);
			// 保存
			commonDao.store(player);

			// 关联
			channel.attr(AttributeKeys.PLAYER).set(player);
			roleNames.add(nickname);
			roles.put(username, player);
			players.put(player.getId(), player);
			return player;
		} catch (Exception e) {
			response.setCode(3);// 创建失败
			log.error(e.getMessage(), e);
		}
		return null;
	}

	/** 登录 */
	public Player login(String username, String token, Channel channel, ResLoginMessage.Builder respone) {

		LoginToken loginToken = tokenMap.get(username);
		if (loginToken == null || !loginToken.getToken().equals(token)) {
			// 如果未找到token或者token不匹配，就去网关请求
			ReqVerifyTokenMessage.Builder builder = ReqVerifyTokenMessage.newBuilder();
			builder.setToken(token);
			builder.setUsername(username);
			ResVerifyTokenMessage message = (ResVerifyTokenMessage) SyncFutureUtils.Intstance.request(builder);
			// 如果token再次匹配正确，就正常登录
			if (message.getCode() != 0 || !token.equals(message.getToken())) {
				respone.setCode(1);// token失效，请重新登录
				return null;
			}
			loginToken = new LoginToken();
			loginToken.setTime(System.currentTimeMillis());
			loginToken.setToken(token);
			loginToken.setUsername(username);
			tokenMap.put(username, loginToken);
		}
		// 验证通过
		channel.attr(AttributeKeys.USERNAME).set(username);

		Player player = roles.get(loginToken.getUsername());
		if (player != null) {
			int state = player.getState();
			if (state == Player.SAVE) {
				respone.setCode(2);// 玩家数据保存中
				return null;
			} else if (player.getSectId() == 0) {
				respone.setCode(4);// 未建立门派
				return null;
			}
		} else {
			player = playerDao.findPlayerByUsername(loginToken.getUsername());
			if (player == null) {
				respone.setCode(3);// 未找到角色
				return null;
			}
		}

		PlayerChannelGroup channelGroup = GameServer.getInstance().getChannelGroup();
		Channel oldChannel = channelGroup.getChannel(player.getId());
		if (oldChannel != null) {
			log.info("玩家:" + player.getId() + "被挤下线！原ip:" + oldChannel.remoteAddress() + " ---> 新ip:"
					+ channel.remoteAddress());

			channelGroup.remove(oldChannel);
			oldChannel.close();
		} else if (channelGroup.size() >= NUMBER_LIMIT) {
			log.debug("在线玩家数量已达上限！");
		}
		// 绑定player与channel
		channel.attr(AttributeKeys.PLAYER).set(player);
		channel.attr(AttributeKeys.UID).set(player.getId());
		channelGroup.add(channel);

		players.put(player.getId(), player);
		roles.put(player.getUsername(), player);

		player.setPreLoginTime(System.currentTimeMillis());

		return player;
	}

	/** 定时销毁令牌 */
	public void clearToken() {
		Iterator<LoginToken> iterator = tokenMap.values().iterator();
		while (iterator.hasNext()) {
			LoginToken loginToken = iterator.next();
			// 令牌保存15分钟
			if (DateUtils.diffMinute(loginToken.getTime()) >= TOKEN_INDATE) {
				tokenMap.remove(loginToken.getUsername());
			}
		}
	}

	/** 获得所有在线的玩家 */
	public final ConcurrentHashMap<Long, Player> getPlayers() {
		return players;
	}

	/** 随机一个名字 */
	public String randomName() {
		for (int i = 0; i < 20; i++) {
			String name = roleRandom.getName();
			if (!roleNames.contains(name)) {
				return name;
			}
		}
		return TextKit.replace(UUID.randomUUID().toString(), "-", "");
	}

	/** 根据账户名得到player(在线不在线都可以) */
	public Player getPlayerById(long uid) {
		Player player = players.get(uid);
		if (player == null) {
			player = playerDao.getEntity(Player.class, uid);
			if (player == null) {
				return null;
			}
			players.putIfAbsent(uid, player);
		}
		return player;
	}

	/** 得到在线的玩家 */
	public Player getOnlinePlayer(long uid) {
		return players.get(uid);
	}

	/**
	 * 昵称是否合法
	 * 
	 * @param name
	 * @return 1不合法 2昵称重复 3门派名重复
	 */
	private boolean nameIsAvailable(String name) {
		if (WordFilter.getInstance().hashBadWords(name)) {
			return true;
		}
		Matcher m = pattern.matcher(name);
		if (!m.matches()) {
			return true;
		}
		if (roleNames.contains(name)) {
			return true;
		}
		return false;
	}

	/**
	 * 定时保存方法
	 */
	public void save() {
		List<GameEntity> entities = new ArrayList<GameEntity>();
		Iterator<Entry<Long, Player>> iterator = players.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Long, Player> entry = iterator.next();
			Player player = entry.getValue();
			if (!player.isOnline()) {
				player.setState(Player.SAVE);
				long roleId = player.getId();
				for (LogoutListener logoutListener : logoutListeners) {
					try {
						logoutListener.logout(roleId, entities);
					} catch (Exception e) {
						log.error(e.getMessage(), e);
					}
				}
				entities.add(player.getSect());
				commonDao.batchUpdate(entities);
				entities.clear();
				iterator.remove();
			}
		}
	}

	/** 退出 */
	public void logout(Player player) {
		player.setState(Player.OFFLINE);
		player.setLastOnlineTime(System.currentTimeMillis());
		log.info(player.getNickname() + ":退出游戏.");
		long preLoginTime = player.getPreLoginTime();
		long now = System.currentTimeMillis();
		int onlineTime = (int) (now - preLoginTime);

		PlayerChannelGroup channelGroup = GameServer.getInstance().getChannelGroup();
		Channel oldChannel = channelGroup.getChannel(player.getId());
		if (oldChannel != null) {
			channelGroup.remove(oldChannel);
		}

		LogManager.getInstance().addLoginLog(player.getId(), player.getNickname(), player.getUsername(),
				player.getChannel(), 2, onlineTime);
	}

	/**
	 * 每天更新
	 */
	public void dailyUpdate() {
		Iterator<Entry<Long, Player>> iterator = players.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Long, Player> entry = iterator.next();
			Player player = entry.getValue();
			try {
				// TODO
				player.toString();
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
	}

	@Override
	public void shutdown() {
		commonDao.batchUpdateCollection(players.values());
	}

	/**
	 * 接收奖励<br>
	 * id:number|id:number|id:number<br>
	 */
	public void reward(Player player, String rewards) {
		try {
			String[] reward = rewards.split("\\|");
			for (String str : reward) {
				String[] s = str.split(":");
				int id = Integer.valueOf(s[0]);
				int num = Integer.valueOf(s[1]);
				reward(player, id, num);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/***
	 * 发送奖励
	 * 
	 * @param player
	 * @param itemId
	 *            物品的模版id
	 * @param number
	 *            物品数量
	 */
	public void reward(Player player, int itemId, int number) {
		switch (itemId) {
		case 1:
			
			break;

		default:
			break;
		}
	}

}
