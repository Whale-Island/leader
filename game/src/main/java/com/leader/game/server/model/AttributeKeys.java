package com.leader.game.server.model;

import com.leader.game.player.model.Player;

import io.netty.util.AttributeKey;

/**
 * netty channel变量keys
 * 
 * @author
 */
public interface AttributeKeys {
	/** 角色id */
	AttributeKey<Long> UID = AttributeKey.newInstance("UID");
	/** 帐号(代表是否验证) */
	AttributeKey<String> USERNAME = AttributeKey.newInstance("USERNAME");
	/** 玩家角色(代表是否登录) */
	AttributeKey<Player> PLAYER = AttributeKey.newInstance("PLAYER");
	/** 密匙 */
	AttributeKey<String> PRIAVTE_KEY = AttributeKey.newInstance("PRIAVTE_KEY");
}
