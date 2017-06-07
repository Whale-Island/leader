package com.leader.game.data.model;

import java.util.HashMap;

import com.leader.game.data.Element;
import com.leader.game.data.Scope;

import lombok.Getter;
import lombok.Setter;

public class RoleEventData {
	private @Getter @Setter int id;
	/** 事件名称/选项名称 */
	private @Getter @Setter String name;
	/** 角色id */
	private @Getter @Setter int roleId;
	/**
	 * 事件类型<br>
	 * 1招呼对白 2背包-穿戴成功 3背包-拿取私人物品 4关系-友好 5关系-告白 6关系-门派 7关系-赠送 8关系-经历 9培养-学习成功
	 * 10培养-转职成功 11职位-本领 12职位-委任 13外出-探索 14外出-外出 15外出-约会 16特殊事件
	 */
	private @Getter @Setter int type;
	/** 好感度要求(范围) */
	private @Getter @Setter Scope cs_favor;

	/** 技艺要求(type:number|type:number|type:number) */
	private @Getter @Setter String c_art;

	/** 其他角色的好感度要求(id:num-num|id:num-num|id:num-num) */
	@Element(generic = { Integer.class, Scope.class })
	private @Getter @Setter HashMap<Integer, Scope> cs_other_favor;

	/** 威望要求(范围) */
	private @Getter @Setter Scope cs_prestige;

	/** 平均好感度要求(范围) */
	private @Getter @Setter Scope cs_av_favor;

	/** 相关物品id */
	private @Getter @Setter int itemId;

	/** 对白id */
	private @Getter @Setter int dialogueId;

	/** 后续子剧情id */
	private @Getter @Setter int[] sonIds;

	/** 奖励物品id:num|id:num|id:num) */
	private @Getter @Setter String rewards;
}
