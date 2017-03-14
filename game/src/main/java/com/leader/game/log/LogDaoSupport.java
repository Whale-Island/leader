package com.leader.game.log;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(value = "logTM", isolation = Isolation.READ_UNCOMMITTED)
public class LogDaoSupport {

	@PersistenceContext(unitName = "logEntityManagerFactory")
	protected EntityManager entityManager;

	public <T> void save(T entity) {
		entityManager.persist(entity);
	}

	public void batchInsert(List<LogEntity> list) {
		int length = list.size();
		for (int i = 0; i < length; i++) {
			try {
				entityManager.persist(list.get(i));
			} catch (Exception e) {
			}
		}
		entityManager.flush();
		entityManager.clear();
	}

}
