package com.leader.game.log.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.leader.game.log.LogEntity;

@Entity
@Table(name = "OnlineNumberLog")
public class OnlineNumberLog implements LogEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1307947641294368063L;
	@Id
	@Column(name = "UID", unique = true, nullable = false)
	private Long id;
	private Date todayTime;
	private int hour;
	private int num;
	private int serverId;

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
	 * @return the todayTime
	 */
	public final Date getTodayTime() {
		return todayTime;
	}

	/**
	 * @param todayTime
	 *            the todayTime to set
	 */
	public final void setTodayTime(Date todayTime) {
		this.todayTime = todayTime;
	}

	/**
	 * @return the hour
	 */
	public final int getHour() {
		return hour;
	}

	/**
	 * @param hour
	 *            the hour to set
	 */
	public final void setHour(int hour) {
		this.hour = hour;
	}

	/**
	 * @return the num
	 */
	public final int getNum() {
		return num;
	}

	/**
	 * @param num
	 *            the num to set
	 */
	public final void setNum(int num) {
		this.num = num;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OnlineNumberLog [id=").append(id).append(", todayTime=").append(todayTime).append(", hour=")
				.append(hour).append(", num=").append(num).append(", serverId=").append(serverId).append("]");
		return builder.toString();
	}

}
