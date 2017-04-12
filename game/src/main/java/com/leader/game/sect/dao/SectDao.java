package com.leader.game.sect.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.leader.core.db.JpaDaoSupport;
import com.leader.game.sect.model.Sect;

public class SectDao extends JpaDaoSupport {

	@Transactional(value = "gameTM", isolation = Isolation.READ_COMMITTED, readOnly = true)
	public Sect findByID(long id) {
		TypedQuery<Sect> query = entityManager.createNamedQuery("Sect.findById", Sect.class);
		query.setParameter("id", id);
		List<Sect> sects = query.getResultList();
		if (sects == null || sects.isEmpty()) {
			return null;
		}
		return sects.get(0);
	}

	@Transactional(value = "gameTM", isolation = Isolation.READ_COMMITTED, readOnly = true)
	public Sect findByPlayerID(long id) {
		TypedQuery<Sect> query = entityManager.createNamedQuery("Sect.findById", Sect.class);
		query.setParameter("id", id);
		List<Sect> sects = query.getResultList();
		if (sects == null || sects.isEmpty()) {
			return null;
		}
		return sects.get(0);
	}

	@Transactional(value = "gameTM", isolation = Isolation.READ_COMMITTED, readOnly = true)
	public Sect findByName(String name) {
		TypedQuery<Sect> query = entityManager.createNamedQuery("Sect.findByName", Sect.class);
		query.setParameter("name", name);
		List<Sect> sects = query.getResultList();
		if (sects == null || sects.isEmpty()) {
			return null;
		}
		return sects.get(0);
	}

	@Transactional(value = "gameTM", isolation = Isolation.READ_COMMITTED, readOnly = true)
	@SuppressWarnings("unchecked")
	public List<String> loadName() {
		Query query = entityManager.createNamedQuery("Sect.getAllName");
		List<String> names = query.getResultList();
		if (names == null || names.isEmpty()) {
			return null;
		}
		return names;
	}
}
