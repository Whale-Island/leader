package com.leader.game.player.listener;

import com.leader.game.player.model.Player;

/** 属性变化监听 */
public interface PropertyChangeListener {
	public static final int LEVEL = 1;

	public void propertyChange(Player player, int type);
}
