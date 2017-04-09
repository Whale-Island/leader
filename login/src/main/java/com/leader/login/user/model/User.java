package com.leader.login.user.model;

import javax.persistence.Entity;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.leader.core.db.GameEntity;

@Entity
@Table(name = "user")
@NamedQueries({ // start
		@NamedQuery(name = "User.findAll", query = "SELECT r FROM User r"),
		@NamedQuery(name = "User.findUserById", query = "SELECT r FROM User r WHERE r.id = ?1"),
		@NamedQuery(name = "User.findUserByName", query = "SELECT r FROM User r WHERE r.username = :username") // end
})
@NamedNativeQueries({ // start BriefInfo
		@NamedNativeQuery(name = "User.getAllUsername", query = "SELECT username FROM User", resultSetMapping = "User.username"), })
public class User implements GameEntity {
	private static final long serialVersionUID = -3826408541194098793L;

	private long id;
	private String username;
	private String password;

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

}
