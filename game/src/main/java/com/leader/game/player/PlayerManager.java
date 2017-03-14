package com.leader.game.player;

import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.leader.game.player.model.Player;

public class PlayerManager {
	/** 角色名表达式 */
	private static final String regEx = "^[\u4e00-\u9fa5_a-zA-Z0-9]+$";
	private static final Pattern pattern = Pattern.compile(regEx);
	/** log */
	private Logger log = LoggerFactory.getLogger(PlayerManager.class);
	/** 所有玩家 */
	private ConcurrentHashMap<String, Player> roles = new ConcurrentHashMap<String, Player>();
	/** 所有玩家 */
	private ConcurrentHashMap<Long, Player> players = new ConcurrentHashMap<Long, Player>();

	private PlayerManager() {
	}

	private static class SigletonHolder {
		static final PlayerManager INSTANCE = new PlayerManager();
	}

	public static PlayerManager getInstance() {
		return SigletonHolder.INSTANCE;
	}
}
