package com.leader.game.event;

import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;

import com.leader.core.server.model.ShutdownListener;
import com.leader.core.util.StringUtils;
import com.leader.game.data.Scope;
import com.leader.game.data.container.RoleEventDataContainer;
import com.leader.game.data.model.RoleEventData;
import com.leader.game.player.model.Player;
import com.leader.game.role.model.Role;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventManager implements ShutdownListener {
	private static class SigletonHolder {
		static final EventManager INSTANCE = new EventManager();
	}

	public static EventManager getInstance() {
		return SigletonHolder.INSTANCE;
	}

	@Autowired
	RoleEventDataContainer container;

	/***
	 * 玩家触发事件
	 * 
	 * @param player
	 * @param type
	 * @param roleId
	 */
	public void trigger(Player player, int type, int roleId) {

	}

	/***
	 * 玩家触发事件(指定事件)
	 * 
	 * @param player
	 * @param id
	 */
	public void trigger(Player player, int id) {
		RoleEventData data = container.getMap().get(id);
		if (data == null) {
			log.error("事件id-{}不存在!", id);
			return;
		}
		Role role = player.getSect().getRoleMap().get(data.getRoleId());
		if (role == null) {
			log.error("玩家id-{}不存在id-{}的弟子!", player.getId(), data.getRoleId());
			return;
		}
		// 校验好感度要求
		Scope scope = data.getCs_favor();
		if (scope != null) {
			int relation = role.getRelationMap().get(player.getRole().getModelId());
			if (!scope.isInfraMetas(relation)) {
				return;
			}
		}

		// 校验技艺要求
		String c_art = data.getC_art();
		if (StringUtils.isNotBlank(c_art)) {
			String[] arts = c_art.split("\\|");
			int num;
			for (String str : arts) {
				String[] art = str.split(":");
				num = role.getArt(Integer.valueOf(art[0]));
				if (num <= Integer.valueOf(art[1]))
					return;
			}
		}

		// 校验其他角色的好感度要求
		HashMap<Integer, Scope> cs_other_favor = data.getCs_other_favor();
		if (cs_other_favor != null) {
			int num;
			for (Entry<Integer, Scope> entry : cs_other_favor.entrySet()) {
				num = role.getRelationMap().get(entry.getKey());
				if (!entry.getValue().isInfraMetas(num))
					return;
			}
		}

		// 威望要求
		scope = data.getCs_prestige();
		if (scope != null && !scope.isInfraMetas(player.getSect().getPrestige()))
			return;

		// 平均好感度要求
		scope = data.getCs_av_favor();
		if (scope != null) {
			ListIterator<Role> iterator = player.getSect().getRoles().listIterator();
			int sum = 0;
			Role r;
			while (iterator.hasNext()) {
				r = iterator.next();
				sum += r.getRelationMap().get(player.getRole().getModelId());
			}
			if (!scope.isInfraMetas(sum))
				return;
		}

		// 触发奖励
		String rewards = data.getRewards();
		if (StringUtils.isNotBlank(rewards)) {

		}

		// 通知客户端触发事件
	}

	@Override
	public void shutdown() {

	}

}
