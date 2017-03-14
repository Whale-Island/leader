package com.leader.game.player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.protobuf.Message.Builder;
import com.leader.core.db.CommonDao;
import com.leader.core.db.GameEntity;
import com.leader.core.server.model.ShutdownListener;
import com.leader.core.util.TextKit;
import com.leader.game.log.manager.LogManager;
import com.leader.game.player.dao.PlayerDao;
import com.leader.game.player.listener.LogoutListener;
import com.leader.game.player.model.Player;
import com.leader.game.player.model.RandomName;
import com.leader.game.server.GameServer;
import com.leader.game.server.model.AttributeKeys;
import com.leader.game.util.WordFilter;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

public class PlayerManager implements ShutdownListener {
	/** 角色名表达式 */
	private static final String regEx = "^[\u4e00-\u9fa5_a-zA-Z0-9]+$";
	private static final Pattern pattern = Pattern.compile(regEx);
	/** log */
	private Logger log = LoggerFactory.getLogger(PlayerManager.class);
	/** 所有玩家 */
	private ConcurrentHashMap<Long, Player> players = new ConcurrentHashMap<Long, Player>();
	/** 所有角色名 */
	private Set<String> roleNames = new HashSet<String>();
	private RandomName randomName;
	/** 退出游戏Listener */
	private List<LogoutListener> logoutListeners;
	@Autowired
	PlayerDao playerDao;
	@Autowired
	CommonDao commonDao;

	/** 所有随机名字保存类 */

	private PlayerManager() {
	}

	private static class SigletonHolder {
		static final PlayerManager INSTANCE = new PlayerManager();
	}

	public static PlayerManager getInstance() {
		return SigletonHolder.INSTANCE;
	}

	/** 初始化调用 */
	public void init() {
		List<String> strings = playerDao.loadNickname("Player.getAllNickname");
		if (strings != null && !strings.isEmpty()) {
			roleNames.addAll(strings);
		}
		// TODO
		randomName = new RandomName();
	}

	/** 网络连接关闭监听器 */
	private final ChannelFutureListener futureListener = new ChannelFutureListener() {
		@Override
		public void operationComplete(ChannelFuture future) throws Exception {
			Channel channel = future.channel();
			Player player = channel.attr(AttributeKeys.ROLE).get();
			if (player != null) {
				logout(player);
			}
			log.info("remote host " + channel.remoteAddress() + " is closed.");
		}
	};

	/** 获得所有在线的玩家 */
	public final ConcurrentHashMap<Long, Player> getPlayers() {
		return players;
	}

	/** 随机一个名字 */
	public String randomName() {
		for (int i = 0; i < 20; i++) {
			String name = randomName.getName();
			if (!roleNames.contains(name)) {
				return name;
			}
		}
		return TextKit.replace(UUID.randomUUID().toString(), "-", "");
	}

	/** 根据账户名得到player(在线不在线都可以) */
	public Player getPlayerById(long roleId) {
		Player player = players.get(roleId);
		if (player == null) {
			player = playerDao.getEntity(Player.class, roleId);
			if (player == null) {
				return null;
			}
			players.putIfAbsent(roleId, player);
		}
		return player;
	}

	/** 得到在线的玩家 */
	public Player getOnlinePlayer(long roleId) {
		return players.get(roleId);
	}

	/** 昵称是否合法 */
	public int roleNameIsAvailable(String roleName) {
		if (WordFilter.getInstance().hashBadWords(roleName)) {
			return 1;
		}
		Matcher m = pattern.matcher(roleName);
		if (!m.matches()) {
			return 1;
		}
		if (roleNames.contains(roleName)) {
			return 2;
		}
		return 0;
	}

	/**
	 * 清理方法
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
				entities.add(player);
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
		LogManager.getInstance().addLoginLog(player.getId(), player.getNickname(), player.getUsername(), 0, 2,
				onlineTime, 0);
	}

	public ChannelFutureListener getFutureListener() {
		return futureListener;
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

	/**
	 * 通知玩家消息
	 *
	 * @param me
	 *            玩家
	 * @param message
	 *            消息
	 */
	public static void tell_player_message(Player player, Builder message) {
		Channel channel = GameServer.getInstance().getChannelGroup().getChannel(player.getId());
		if (channel == null || !channel.isActive()) {
			return;
		}
		channel.writeAndFlush(message);
	}

	@Override
	public void shutdown() {
		commonDao.batchUpdateCollection(players.values());
	}

	public List<LogoutListener> getLogoutListeners() {
		return logoutListeners;
	}

	public void setLogoutListeners(List<LogoutListener> logoutListeners) {
		this.logoutListeners = logoutListeners;
	}
}
