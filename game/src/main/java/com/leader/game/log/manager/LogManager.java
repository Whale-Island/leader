package com.leader.game.log.manager;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.leader.game.log.bean.LoginLog;
import com.leader.game.log.bean.OnlineNumberLog;
import com.leader.game.log.bean.RegisterLog;
import com.leader.game.server.GameServer;
import com.leader.game.server.model.ServerConfig;
import com.leader.game.server.thread.LogProcessThread;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 日志
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public enum LogManager {
	Intstance;

	@Autowired
	LogProcessThread logProcessThread;
	@Autowired
	ServerConfig serverConfig;

	/**
	 * 添加登录日志
	 * 
	 * @param roleId
	 * @param nickname
	 * @param username
	 * @param channel
	 * @param type
	 *            1登录 2下线
	 * @param onlineTime
	 */
	public void addLoginLog(long roleId, String nickname, String username, int channel, int type, int onlineTime) {
		LoginLog loginLog = new LoginLog();
		loginLog.setLoginTime(new Date());
		loginLog.setServerId(serverConfig.getServerId());
		loginLog.setChannelId(channel);
		loginLog.setRoleId(roleId);
		loginLog.setNickname(nickname);
		loginLog.setUsername(username);
		loginLog.setType(type);
		loginLog.setOnlineTime(onlineTime);
		logProcessThread.put(loginLog);
	}

	/**
	 * 添加注册日志
	 * 
	 * @param roleId
	 * @param deviceId
	 * @param nickname
	 * @param username
	 * @param channel
	 */
	public void addRegisterLog(long roleId, String deviceId, String nickname, String username, int channel) {
		RegisterLog log = new RegisterLog();
		log.setDeviceId(deviceId);
		log.setRegisterTime(new Date());
		log.setNickname(nickname);
		log.setUsername(username);
		log.setChannelId(channel);
		log.setServerId(serverConfig.getServerId());
		logProcessThread.put(log);
	}

	/**
	 * 添加在线日志
	 * 
	 * @param hour
	 */
	public void addOnlineNumberLog(int hour) {
		OnlineNumberLog numberLog = new OnlineNumberLog();
		numberLog.setHour(hour);
		numberLog.setNum(GameServer.Intstance.getChannelGroup().size());
		numberLog.setServerId(serverConfig.getServerId());
		numberLog.setTodayTime(new Date());
		numberLog.setId(1L);
		logProcessThread.put(numberLog);
	}

}
