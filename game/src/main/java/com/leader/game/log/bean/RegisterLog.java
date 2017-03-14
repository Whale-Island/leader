package com.leader.game.log.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.leader.game.log.LogEntity;

/**
 * 注册log
 * 
 * @author
 *
 */
@Entity
@Table(name = "RegisterLog")
public class RegisterLog implements LogEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3770903200790518243L;

	/** id */
	@Id
	@Column(name = "PLAYER_ID", unique = true, nullable = false)
	private long id;
	/** 账户名 */
	private String userName;
	/** 角色名 */
	private String roleName;
	/** 注册时间 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date registerTime;
	/** 设备号 */
	private String deviceId;
	/** 渠道 */
	private int channelId;
	/** 大区编号 */
	private int serverId;

	/**
	 * @return the id
	 */
	public final long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public final void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the userName
	 */
	public final String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public final void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the roleName
	 */
	public final String getRoleName() {
		return roleName;
	}

	/**
	 * @param roleName
	 *            the roleName to set
	 */
	public final void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * @return the registerTime
	 */
	public final Date getRegisterTime() {
		return registerTime;
	}

	/**
	 * @param registerTime
	 *            the registerTime to set
	 */
	public final void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	/**
	 * @return the deviceId
	 */
	public final String getDeviceId() {
		return deviceId;
	}

	/**
	 * @param deviceId
	 *            the deviceId to set
	 */
	public final void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * @return the channelId
	 */
	public final int getChannelId() {
		return channelId;
	}

	/**
	 * @param channelId
	 *            the channelId to set
	 */
	public final void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	/**
	 * @return the serverId
	 */
	public final int getServerId() {
		return serverId;
	}

	/**
	 * @param serverId
	 *            the serverId to set
	 */
	public final void setServerId(int serverId) {
		this.serverId = serverId;
	}

}
