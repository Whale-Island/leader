package com.leader.game.log.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.leader.game.log.LogEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 注册log
 * 
 * @author
 *
 */
@Entity
@ToString
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
	private @Getter @Setter String username;
	/** 角色名 */
	private @Getter @Setter String nickname;
	/** 注册时间 */
	@Temporal(TemporalType.TIMESTAMP)
	private @Getter @Setter Date registerTime;
	/** 设备号 */
	private @Getter @Setter String deviceId;
	/** 渠道 */
	private @Getter @Setter int channelId;
	/** 大区编号 */
	private @Getter @Setter int serverId;

}
