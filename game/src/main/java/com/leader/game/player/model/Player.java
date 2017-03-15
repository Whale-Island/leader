package com.leader.game.player.model;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.leader.core.db.GameEntity;

@Entity
@Table(name = "Player")
@NamedQueries({ // start
		@NamedQuery(name = "Player.findAll", query = "SELECT r FROM Player r"),
		@NamedQuery(name = "Player.findPlayerById", query = "SELECT r FROM Player r WHERE r.id = ?1"),
		@NamedQuery(name = "Player.findPlayerByName", query = "SELECT r FROM Player r WHERE r.username = :username") // end
})
@NamedNativeQueries({ // start BriefInfo
		@NamedNativeQuery(name = "Player.getAllNickname", query = "SELECT nickname FROM Player", resultSetMapping = "Player.nickname"), })
@SqlResultSetMappings({ // start
		@SqlResultSetMapping(name = "Player.nickname", columns = {
				@ColumnResult(name = "nickname", type = String.class) }) // end
})
public class Player implements GameEntity {
	private static final long serialVersionUID = 1184515259071679517L;

	@Id
	@Column(unique = true, nullable = false)
	private long id;
	/** 帐号(冗余字段) */
	private String username;
	/** 昵称 */
	private String nickname;
	/** 性别 */
	private short sex;
	/** 等级 */
	private int level;
	/** 最后一次在线时间 */
	private long lastOnlineTime;
	/** 上次登录时间 */
	private long preLoginTime;
	/** 平台渠道 */
	private int gameChannel;
	/** 状态 */
	private @Transient volatile int state;
	/** 1.在线 2.离线 */
	public static final int ONLINE = 1, OFFLINE = 2, SAVE = 3;

	/**
	 * @return the isOnline
	 */
	public final boolean isOnline() {
		return state == ONLINE;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public long getLastOnlineTime() {
		return lastOnlineTime;
	}

	public void setLastOnlineTime(long lastOnlineTime) {
		this.lastOnlineTime = lastOnlineTime;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public long getPreLoginTime() {
		return preLoginTime;
	}

	public void setPreLoginTime(long preLoginTime) {
		this.preLoginTime = preLoginTime;
	}

	public int getGameChannel() {
		return gameChannel;
	}

	public void setGameChannel(int gameChannel) {
		this.gameChannel = gameChannel;
	}

	public short getSex() {
		return sex;
	}

	public void setSex(short sex) {
		this.sex = sex;
	}

}
