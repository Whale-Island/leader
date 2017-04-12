package com.leader.game.role.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.leader.core.db.JpaDaoSupport;
import com.leader.game.role.model.Role;

public class RoleDao extends JpaDaoSupport {
	@Transactional(value = "gameTM", isolation = Isolation.READ_COMMITTED, readOnly = true)
	public List<Role> findBySectId(long id) {
		TypedQuery<Role> query = entityManager.createNamedQuery("Role.findBySectId", Role.class);
		query.setParameter(0, id);
		List<Role> sects = query.getResultList();
		if (sects == null || sects.isEmpty()) {
			return null;
		}
		return sects;
	}

	@Transactional(value = "gameTM", isolation = Isolation.READ_COMMITTED, readOnly = true)
	public Role findById(long id) {
		TypedQuery<Role> query = entityManager.createNamedQuery("Role.findById", Role.class);
		query.setParameter(0, id);
		List<Role> sects = query.getResultList();
		if (sects == null || sects.isEmpty()) {
			return null;
		}
		return sects.get(0);
	}
}
