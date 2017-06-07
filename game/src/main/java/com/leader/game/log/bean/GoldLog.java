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
@Table(name = "goldlog")
public class GoldLog implements LogEntity {
	private static final long serialVersionUID = 8472150834756083137L;
	@Id
	@Column(name = "UID", unique = true, nullable = false)
	private @Getter @Setter long id;
	/** 角色id */
	private @Getter @Setter long roleId;
	/** 服务器编号 */
	private @Getter @Setter int serverId;
	/** 角色昵称 */
	private @Getter @Setter String nickname;
	/** 帐号 */
	private @Getter @Setter String username;
	/** 时间 */
	@Temporal(TemporalType.TIMESTAMP)
	private @Getter @Setter Date time;
	/** 变化前的值 */
	private @Getter @Setter long preNum;
	/** 变化值 */
	private @Getter @Setter long value;
	/** 现在值 */
	private @Getter @Setter long num;
}
