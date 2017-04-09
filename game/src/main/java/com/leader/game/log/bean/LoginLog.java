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

@Entity
@ToString
@Table(name = "loginlog")
public class LoginLog implements LogEntity {
	private static final long serialVersionUID = 4231937768212145954L;
	@Id
	@Column(name = "UID", unique = true, nullable = false)
	private @Getter @Setter Long id;
	/** 大区编号 */
	private @Getter @Setter int serverId;
	/** 渠道 */
	private @Getter @Setter int channelId;
	/** id */
	private @Getter @Setter long roleId;
	/** 账户名 */
	private @Getter @Setter String username;
	/** 角色名 */
	private @Getter @Setter String nickname;
	/** 登录时间 */
	@Temporal(TemporalType.TIMESTAMP)
	private @Getter @Setter Date loginTime;
	/** 类型 1登录 2下线 */
	private @Getter @Setter int type;
	/** 在线时长 */
	private @Getter @Setter int onlineTime;

}
