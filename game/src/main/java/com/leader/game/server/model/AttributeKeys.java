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
	AttributeKey<Integer> ROLE_ID = AttributeKey.newInstance("ROLE_ID");
	/** 玩家角色 */
	AttributeKey<Player> ROLE = AttributeKey.newInstance("ROLE");
	/** 服务器id */
	AttributeKey<Integer> SERVER_ID = AttributeKey.newInstance("ServerId");
	/** 渠道id */
	AttributeKey<Integer> CHANNEL_ID = AttributeKey.newInstance("ChannelId");
	/** 密匙 */
	AttributeKey<String> PRIAVTE_KEY = AttributeKey.newInstance("PRIAVTE_KEY");
}
