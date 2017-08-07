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
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
	 * @param builder
	 * @return
	 */
	public void register(String username, String password, JSONObject json) {
		try {
			// 帐号名已存在
			if (names.contains(username)) {
				json.put("code", 3);
				json.put("msg", "The name already exists");
				return;
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

			json.put("token", token);
		} catch (Exception e) {
			json.put("code", 500);
			json.put("msg", "Internal Server Error.");
			log.error(e.getMessage(), e);
		}
		return;
	}

	/***
	 * 用户登录
	 * 
	 * @param map
	 * @param json
	 * @return
	 */
	public void login(String username, String password, JSONObject json) {
		// 帐号或密码不能为空
		if (StringUtil.isNullOrEmpty(username) || StringUtil.isNullOrEmpty(password)) {
			json.put("code", 1);
			json.put("msg", "The username or password is empty.");
			return;
		} else {
			User user = dao.findPlayerByUserName(username);
			// 密码不符
			if (user == null || !user.getPassword().equals(password)) {
				json.put("code", 2);
				json.put("msg", "The username or password is invalid.");
				return;
			}
			String token = addToken(user.getUsername());
			json.put("token", token);

			List<Role> roles = dao.loadRole(user.getUsername());
			json.put("roles", roles);
		}
		return;
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
	 * @param serverID
	 * @param nickname
	 * @param sex
	 * @param level
	 */
	public void updateRole(String username, int serverID, String nickname, short sex, int level) {
		Role role = dao.findRole(username, serverID);
		if (role == null) {
			role = new Role();
			role.setUsername(username);
			role.setServerID(serverID);
			Server server = LoginServer.getInstance().getServers().getOrDefault(serverID, new Server());
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
