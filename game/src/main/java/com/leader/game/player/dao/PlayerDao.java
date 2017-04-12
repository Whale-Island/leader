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
	public Player findPlayerById(long id) {
		TypedQuery<Player> query = entityManager.createNamedQuery("Player.findPlayerById", Player.class);
		query.setParameter(0, id);
		List<Player> players = query.getResultList();
		if (players == null || players.isEmpty()) {
			return null;
		}
		return players.get(0);
	}

	@Transactional(value = "gameTM", isolation = Isolation.READ_COMMITTED, readOnly = true)
	public Player findPlayerByUsername(String username) {
		TypedQuery<Player> query = entityManager.createNamedQuery("Player.findPlayerByName", Player.class);
		query.setParameter("username", username);
		List<Player> players = query.getResultList();
		if (players == null || players.isEmpty()) {
			return null;
		}
		return players.get(0);
	}

	@Transactional(value = "gameTM", isolation = Isolation.READ_COMMITTED, readOnly = true)
	@SuppressWarnings("unchecked")
	public List<String> loadNickname() {
		Query query = entityManager.createNamedQuery("Player.getAllNickname");
		List<String> names = query.getResultList();
		if (names == null || names.isEmpty()) {
			return null;
		}
		return names;
	}

}
