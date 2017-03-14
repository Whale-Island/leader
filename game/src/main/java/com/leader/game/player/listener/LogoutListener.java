package com.leader.game.player.listener;

import java.util.List;

import com.leader.core.db.GameEntity;

/**
 * 退出游戏监听器
 * 
 * @author
 *
 */
public interface LogoutListener {

	/**
	 * 退出方法
	 */
	public void logout(long roleId, List<GameEntity> entyties);
}
