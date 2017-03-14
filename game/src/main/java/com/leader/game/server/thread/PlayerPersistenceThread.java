package com.leader.game.server.thread;

import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.leader.core.db.CommonDao;
import com.leader.core.server.model.ThreadAdapter;
import com.leader.game.player.model.Player;

@Component
public class PlayerPersistenceThread extends ThreadAdapter {

	// 日志
	private Logger log = LoggerFactory.getLogger(getClass());
	// 运行标志
	private volatile boolean stop = false;
	private LinkedBlockingQueue<Player> role_queue = new LinkedBlockingQueue<Player>();
	@Autowired
	private CommonDao dao;

	public PlayerPersistenceThread() {
		super("Player-Persistence-Thread");
	}

	public PlayerPersistenceThread(String name) {
		super(name);
	}

	@Override
	public void run() {
		StringBuilder builder = new StringBuilder();
		while (!stop || !role_queue.isEmpty()) {
			try {
				Player player = role_queue.take();
				dao.store(player);
				builder.setLength(0);
				log.info(builder.toString());
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			} finally {
			}
		}
		log.info("PlayerPersistenceThread End.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cytx.pokemon.server.thread.ThreadAdapter#stop0()
	 */
	@Override
	public void stop0() {
		stop = true;
	}

	/**
	 * 添加角色数据(不会阻塞)
	 *
	 * @param player
	 *            角色
	 */
	public boolean addRole(Player player) {
		return role_queue.offer(player);
	}

	/**
	 * 添加角色数据(会阻塞)
	 *
	 * @param player
	 *            角色
	 */
	public void poll(Player player) {
		try {
			role_queue.put(player);
		} catch (InterruptedException e) {
			log.error("poll player error,player = " + player, e);
		}
	}
}
