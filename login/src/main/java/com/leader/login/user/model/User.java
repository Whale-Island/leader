package com.leader.login.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.leader.core.db.GameEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
@NamedQueries({ // start
		@NamedQuery(name = "User.findAll", query = "SELECT r FROM User r"),
		@NamedQuery(name = "User.findUserByID", query = "SELECT r FROM User r WHERE r.id = ?1"),
		@NamedQuery(name = "User.findUserByName", query = "SELECT r FROM User r WHERE r.username = :username") // end
})
public class User implements GameEntity {
	private static final long serialVersionUID = -3826408541194098793L;

	@Id
	@Column(unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private @Getter @Setter long id;
	private @Getter @Setter String username;
	private @Getter @Setter String password;

}
