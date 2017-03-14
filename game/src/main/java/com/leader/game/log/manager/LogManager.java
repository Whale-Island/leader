package com.leader.game.log.manager;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.leader.game.log.bean.LoginLog;
import com.leader.game.log.bean.OnlineNumberLog;
import com.leader.game.log.bean.RegisterLog;
import com.leader.game.server.GameServer;
import com.leader.game.server.model.ServerConfig;
import com.leader.game.server.thread.LogProcessThread;

/**
 * 日志
 * 
 * @author
 *
 */
public class LogManager {

	@Autowired
	LogProcessThread logProcessThread;
	@Autowired
	ServerConfig serverConfig;

	private LogManager() {
	}

	private static class SigletonHolder {
		private static final LogManager INSTANCE = new LogManager();
	}

	public static LogManager getInstance() {
		return SigletonHolder.INSTANCE;
	}

	/**
	 * 添加登录日志
	 * 
	 * @param roleId
	 * @param roleName
	 * @param userName
	 * @param channel
	 * @param type
	 * @param onlineTime
	 */
	public void addLoginLog(long roleId, String roleName, String userName, int channel, int type, int onlineTime,
			int count) {
		LoginLog loginLog = new LoginLog();
		loginLog.setLoginTime(new Date());
		loginLog.setRoleId(roleId);
		loginLog.setRoleName(roleName);
		loginLog.setUserName(userName);
		loginLog.setChannelId(channel);
		loginLog.setServerId(serverConfig.getServerId());
		loginLog.setId(1L);
		loginLog.setType(type);
		loginLog.setOnlineTime(onlineTime);
		loginLog.setCount(count);
		logProcessThread.put(loginLog);
	}

	/**
	 * 添加注册日志
	 * 
	 * @param roleId
	 * @param deviceId
	 * @param roleName
	 * @param userName
	 * @param channel
	 */
	public void addRegisterLog(long roleId, String deviceId, String roleName, String userName, int channel) {
		RegisterLog log = new RegisterLog();
		log.setId(roleId);
		log.setDeviceId(deviceId);
		log.setRegisterTime(new Date());
		log.setRoleName(roleName);
		log.setUserName(userName);
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
		numberLog.setNum(GameServer.getInstance().getChannelGroup().size());
		numberLog.setServerId(serverConfig.getServerId());
		numberLog.setTodayTime(new Date());
		numberLog.setId(1L);
		logProcessThread.put(numberLog);
	}

}
