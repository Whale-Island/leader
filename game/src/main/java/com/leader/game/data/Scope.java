package com.leader.game.data;

import lombok.Getter;
import lombok.Setter;

/** 取值范围 */
public class Scope {
	/** 取值范围-最小值 */
	private @Getter @Setter int min;
	/** 取值范围-最大值 */
	private @Getter @Setter int max;

	/** 是否在范围内 */
	public boolean isInfraMetas(int num) {
		return num >= min && num <= max;
	}
}
