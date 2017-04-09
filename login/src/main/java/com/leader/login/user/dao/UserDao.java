package com.leader.login.user.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.leader.core.db.JpaDaoSupport;
import com.leader.login.user.model.Role;
import com.leader.login.user.model.User;

@Repository
public class UserDao extends JpaDaoSupport {

	@Transactional(value = "gameTM", isolation = Isolation.READ_COMMITTED, readOnly = true)
	public User findPlayerByUserName(String username) {
		TypedQuery<User> query = entityManager.createNamedQuery("User.findUserByName", User.class);
		query.setParameter("username", username);
		List<User> users = query.getResultList();
		if (users == null || users.isEmpty()) {
			return null;
		}
		return users.get(0);
	}

	@Transactional(value = "gameTM", isolation = Isolation.READ_COMMITTED, readOnly = true)
	@SuppressWarnings("unchecked")
	public List<String> loadUsername() {
		Query query = entityManager.createNamedQuery("User.getAllUsername");
		List<String> names = query.getResultList();
		if (names == null || names.isEmpty()) {
			return null;
		}
		return names;
	}

	@Transactional(value = "gameTM", isolation = Isolation.READ_COMMITTED, readOnly = true)
	@SuppressWarnings("unchecked")
	public List<Role> loadRole(String username) {
		Query query = entityManager.createNamedQuery("Role.findRoleByName");
		query.setParameter("username", username);
		List<Role> roles = query.getResultList();
		if (roles == null || roles.isEmpty()) {
			return null;
		}
		return roles;
	}

	@Transactional(value = "gameTM", isolation = Isolation.READ_COMMITTED, readOnly = true)
	@SuppressWarnings("unchecked")
	public Role findRole(String username, int serverId) {
		Query query = entityManager.createNamedQuery("Role.findRole");
		query.setParameter("username", username);
		query.setParameter("serverId", serverId);
		List<Role> roles = query.getResultList();
		if (roles == null || roles.isEmpty()) {
			return null;
		}
		return roles.get(0);
	}
}
