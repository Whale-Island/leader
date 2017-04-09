package com.leader.login.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.leader.core.db.GameEntity;

@Entity
@Table(name = "role")
@NamedQueries({ // start
		@NamedQuery(name = "Role.findRoleByName", query = "SELECT r FROM Role r WHERE "
				+ "r.username = :username order by serverId,level"),
		@NamedQuery(name = "Role.findRole", query = "SELECT r FROM Role r WHERE "
				+ "r.username = :username and r.serverId = :serverId") // end
})
public class Role implements GameEntity {
	private static final long serialVersionUID = -980826933743988382L;

	@Id
	@Column(unique = true, nullable = false)
	private long id;
	/** 帐号 */
	private String username;
	/** 昵称 */
	private String nickname;
	/** 性别 */
	private short sex;
	/** 等级 */
	private int level;
	/** 所在服务器名称 */
	private String servername;
	/** 所在服务器编号 */
	private int serverId;

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

	public short getSex() {
		return sex;
	}

	public void setSex(short sex) {
		this.sex = sex;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getServername() {
		return servername;
	}

	public void setServername(String servername) {
		this.servername = servername;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

}
