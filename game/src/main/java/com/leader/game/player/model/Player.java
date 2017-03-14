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

	private String username;

	private String password;

	private String nickname;

	private int level;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

}
