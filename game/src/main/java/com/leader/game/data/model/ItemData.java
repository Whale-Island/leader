package com.leader.game.data.model;

import lombok.Getter;
import lombok.Setter;

/** 物品数据表 */
public class ItemData {
	private @Getter @Setter int id;
	/** 名字 */
	private @Getter @Setter String name;
	/** 描述 */
	private @Getter @Setter String describe;
	/** 是否可以使用 */
	private @Getter @Setter int isEnabled;
	/** 是否可以叠加 */
	private @Getter @Setter int isOverlap;
	/** 是否可以出售 */
	private @Getter @Setter int isSell;
}
