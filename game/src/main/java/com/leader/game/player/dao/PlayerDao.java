/**
 * 
 */
package com.leader.game.player.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.leader.core.db.JpaDaoSupport;
import com.leader.game.player.model.Player;

/**
 * dao
 * 
 * @author
 *
 */
@Repository
public class PlayerDao extends JpaDaoSupport {

	@Transactional(value = "gameTM", isolation = Isolation.READ_COMMITTED, readOnly = true)
	public Player findPlayerByUserName(String userName) {
		TypedQuery<Player> query = entityManager.createNamedQuery("Player.findPlayerByName", Player.class);
		query.setParameter("userName", userName);
		List<Player> players = query.getResultList();
		if (players == null || players.isEmpty()) {
			return null;
		}
		return players.get(0);
	}

	@Transactional(value = "gameTM", isolation = Isolation.READ_COMMITTED, readOnly = true)
	@SuppressWarnings("unchecked")
	public List<String> loadNickname(String sql) {
		Query query = entityManager.createNamedQuery(sql);
		List<String> names = query.getResultList();
		if (names == null || names.isEmpty()) {
			return null;
		}
		return names;
	}
}
