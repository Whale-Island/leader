package com.leader.game.storage.model;

import lombok.Getter;
import lombok.Setter;

public class Item {
	private @Getter @Setter int id;
	/** 名字 */
	private @Getter @Setter String name;
	/** 描述 */
	private @Getter @Setter String describe;
	/** 是否可以使用 */
	private @Setter int isEnabled;

	/** 是否可以使用 */
	public boolean isEnabled() {
		return isEnabled == 1;
	}

}
