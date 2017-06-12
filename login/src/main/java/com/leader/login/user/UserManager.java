package com.leader.login.user;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.leader.core.db.CommonDao;
import com.leader.core.server.model.ShutdownListener;
import com.leader.core.util.RandomUtils;
import com.leader.login.server.LoginServer;
import com.leader.login.server.model.Server;
import com.leader.login.user.dao.UserDao;
import com.leader.login.user.model.LoginToken;
import com.leader.login.user.model.Role;
import com.leader.login.user.model.User;

import io.netty.util.internal.StringUtil;

public class UserManager implements ShutdownListener {
	/** 所有帐号名 */
	private Set<String> names = new HashSet<String>();
	/** 登录令牌map */
	private Map<String, LoginToken> tokenMap = new ConcurrentHashMap<String, LoginToken>();
	@Autowired
	private UserDao dao;
	@Autowired
	CommonDao commonDao;

	private UserManager() {
	}

	private static class SigletonHolder {
		static final UserManager INSTANCE = new UserManager();
	}

	public static UserManager getInstance() {
		return SigletonHolder.INSTANCE;
	}

	/***
	 * 注册
	 * 
	 * @param map
	 * @param object
	 * @return
	 */
	public int register(String username, String password, JSONObject object) {
		// 帐号名已存在
		if (names.contains(username)) {
			return 1;
		}
		// 新增用户
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		commonDao.store(user);
		// 记录token用于登录游戏服
		String token = addToken(user.getUsername());
		// 记录帐号名
		names.add(username);

		object.put("token", token);
		return 0;
	}

	/***
	 * 用户登录
	 * 
	 * @param map
	 * @param object
	 * @return
	 */
	public int login(String username, String password, JSONObject object) {
		// 帐号或密码不能为空
		if (StringUtil.isNullOrEmpty(username) || StringUtil.isNullOrEmpty(password)) {
			return 1;
		} else {
			User user = dao.findPlayerByUserName(username);
			// 密码不符
			if (!user.getPassword().equals(password)) {
				return 2;
			}
			String token = addToken(user.getUsername());
			List<Role> roles = dao.loadRole(user.getUsername());

			object.put("token", token);
			object.put("roles", roles);
		}
		return 0;
	}

	private String addToken(String username) {
		// 生成token
		String token = Md5Crypt.apr1Crypt(username + RandomUtils.random(10000));

		// 保存token
		LoginToken loginToken = new LoginToken();
		loginToken.setUsername(username);
		loginToken.setToken(token);
		tokenMap.put(username, loginToken);
		return token;
	}

	/***
	 * 游戏服登录验证令牌
	 * 
	 * @param username
	 * @param token
	 * @return
	 */
	public int verifyToken(String username, String token) {
		LoginToken loginToken = tokenMap.get(username);
		if (loginToken != null && loginToken.getToken().equals(token)) {
			tokenMap.remove(username);// 使用一次即销毁
			return 0;
		}
		return -1;
	}

	/***
	 * 玩家退出游戏时做更新(在线时长超过10分钟)
	 * 
	 * @param username
	 * @param serverId
	 * @param nickname
	 * @param sex
	 * @param level
	 */
	public void updateRole(String username, int serverId, String nickname, short sex, int level) {
		Role role = dao.findRole(username, serverId);
		if (role == null) {
			role = new Role();
			role.setUsername(username);
			role.setServerId(serverId);
			Server server = LoginServer.getInstance().getServers().getOrDefault(serverId, new Server());
			role.setServername(server.getName());
		}
		role.setNickname(nickname);
		role.setSex(sex);
		role.setLevel(level);
		commonDao.store(role);
	}

	@Override
	public void shutdown() {

	}

}
