package com.leader.core.db;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CommonDao extends JpaDaoSupport {

	@Transactional(value = "gameTM", isolation = Isolation.READ_COMMITTED)
	public <T> void store(T entity) {
		entityManager.merge(entity);
	}

	@Transactional(value = "gameTM", isolation = Isolation.READ_COMMITTED, readOnly = true)
	public <T> List<T> listEntity(String sql, Class<T> classType) {
		TypedQuery<T> query = entityManager.createNamedQuery(sql, classType);
		return query.getResultList();
	}

	@Transactional(value = "gameTM", isolation = Isolation.READ_COMMITTED, readOnly = true)
	public <T> List<T> listEntityLimitResult(String sql, Class<T> classType, int maxResult) {
		TypedQuery<T> query = entityManager.createNamedQuery(sql, classType);
		query.setMaxResults(maxResult);
		return query.getResultList();
	}

	@Transactional(value = "gameTM", isolation = Isolation.READ_COMMITTED)
	public <T> void delete(T entity) {
		entityManager.remove(entityManager.merge(entity));
	}

	/**
	 * update data
	 * 
	 * @return the number of entities updated or deleted
	 */
	@Transactional(value = "gameTM", isolation = Isolation.READ_COMMITTED)
	public int executeUpdate(String sql) {
		Query query = entityManager.createNamedQuery(sql);
		return query.executeUpdate();
	}

	@Transactional(value = "gameTM", isolation = Isolation.READ_COMMITTED, readOnly = true)
	public <T> T find(Serializable id, Class<T> entityClass) {
		T t = (T) entityManager.find(entityClass, id);
		return t;
	}

	@Transactional(value = "gameTM", isolation = Isolation.READ_COMMITTED, readOnly = true)
	public <T> List<T> listEntityByRoleId(String sql, Class<T> classType, long roleId) {
		TypedQuery<T> query = entityManager.createNamedQuery(sql, classType);
		query.setParameter("roleId", roleId);
		return query.getResultList();
	}

	@Transactional(value = "gameTM", isolation = Isolation.READ_COMMITTED, readOnly = true)
	public <T> T loadEntityByRoleId(String sql, Class<T> classType, long roleId) {
		TypedQuery<T> query = entityManager.createNamedQuery(sql, classType);
		query.setParameter("roleId", roleId);
		List<T> list = query.getResultList();
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Transactional(value = "gameTM", isolation = Isolation.READ_COMMITTED)
	public void batchUpdate(List<? extends GameEntity> list) {
		if (list == null) {
			return;
		}
		int length = list.size();
		if (length == 0) {
			return;
		}
		for (int i = 0; i < length; i++) {
			try {
				entityManager.merge(list.get(i));
			} catch (Exception e) {
			}
			try {
				if (i % 30 == 0 || i == length - 1) {
					entityManager.flush();
					entityManager.clear();
				}
			} catch (Exception e) {
			}
		}
	}

	@Transactional(value = "gameTM", isolation = Isolation.READ_COMMITTED)
	public void batchUpdateCollection(Collection<? extends GameEntity> list) {
		if (list == null) {
			return;
		}
		int size = list.size();
		if (size == 0) {
			return;
		}
		int n = 0;
		for (GameEntity gameEntity : list) {
			try {
				n++;
				entityManager.merge(gameEntity);
			} catch (Exception e) {
			}
			try {
				if (n % 30 == 0 || n == size) {
					entityManager.flush();
					entityManager.clear();
				}
			} catch (Exception e) {
			}
		}
	}

}
