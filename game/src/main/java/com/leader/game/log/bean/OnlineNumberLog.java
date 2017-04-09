package com.leader.game.log.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.leader.game.log.LogEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString
@Table(name = "OnlineNumberLog")
public class OnlineNumberLog implements LogEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1307947641294368063L;
	@Id
	@Column(name = "UID", unique = true, nullable = false)
	private @Getter @Setter Long id;
	/** 日期 */
	private @Getter @Setter Date todayTime;
	/** 小时 */
	private @Getter @Setter int hour;
	/** 人数 */
	private @Getter @Setter int num;
	/** 服务器号 */
	private @Getter @Setter int serverId;

}
