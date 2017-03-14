package com.leader.game.log.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.leader.game.log.LogEntity;

@Entity
@Table(name = "loginlog")
public class LoginLog implements LogEntity {
	private static final long serialVersionUID = 4231937768212145954L;
	@Id
	@Column(name = "UID", unique = true, nullable = false)
	private Long id;
	/** id */
	private long roleId;
	/** 账户名 */
	private String userName;
	/** 角色名 */
	private String roleName;
	/** 登录时间 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date loginTime;
	/** 渠道 */
	private int channelId;
	/** 大区编号 */
	private int serverId;
	/** 当前时间个注册时间之差 */
	private int count;
	/** 类型 1登录 2下线 */
	private int type;
	/** 在线时长 */
	private int onlineTime;

	/**
	 * @return the id
	 */
	public final Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public final void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the roleId
	 */
	public final long getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId
	 *            the roleId to set
	 */
	public final void setRoleId(long roleId) {
		this.roleId = roleId;
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
	 * @return the loginTime
	 */
	public final Date getLoginTime() {
		return loginTime;
	}

	/**
	 * @param loginTime
	 *            the loginTime to set
	 */
	public final void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
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

	/**
	 * @return the count
	 */
	public final int getCount() {
		return count;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public final void setCount(int count) {
		this.count = count;
	}

	/**
	 * @return the type
	 */
	public final int getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public final void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the onlineTime
	 */
	public final int getOnlineTime() {
		return onlineTime;
	}

	/**
	 * @param onlineTime
	 *            the onlineTime to set
	 */
	public final void setOnlineTime(int onlineTime) {
		this.onlineTime = onlineTime;
	}

}
