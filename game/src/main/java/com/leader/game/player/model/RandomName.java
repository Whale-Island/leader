package com.leader.game.player.model;

import com.leader.game.util.RandomUtils;

public class RandomName {
	/** 姓 */
	private String[] family_names;
	/** 名 */
	private String[] names;

	/**
	 * @return the family_name
	 */
	public final String[] getFamily_names() {
		return family_names;
	}

	/**
	 * @param family_name
	 *            the family_name to set
	 */
	public final void setFamily_names(String[] family_names) {
		this.family_names = family_names;
	}

	/**
	 * @return the names
	 */
	public final String[] getNames() {
		return names;
	}

	/**
	 * @param names
	 *            the names to set
	 */
	public final void setNames(String[] names) {
		this.names = names;
	}

	/**
	 * 随机一个名字
	 * 
	 * @return
	 */
	public String getName() {
		return family_names[RandomUtils.random(0, family_names.length - 1)]
				+ names[RandomUtils.random(0, names.length - 1)];
	}

}
