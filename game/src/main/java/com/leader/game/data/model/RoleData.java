package com.leader.game.data.model;

import java.util.HashMap;

import com.leader.game.data.Element;
import com.leader.game.data.Scope;

import lombok.Getter;
import lombok.Setter;

public class RoleData {
	private @Getter @Setter int id;
	/** 名字 */
	@Element(generic = { Integer.class, Scope.class })
	private @Getter @Setter HashMap<Integer, Scope> name;
	/** 职业(仅显示) */
	private @Getter @Setter String profession;
	/** 资质(id) */
	private @Getter @Setter int aptitude;
	/** 天赋技能(id) */
	private @Getter @Setter int talent;
	/** 喜欢的事物(id,id,id) */
	private @Getter @Setter int[] favorite;

	/** 精 */
	private @Getter @Setter int energy;
	/** 气 */
	private @Getter @Setter int breath;
	/** 神 */
	private @Getter @Setter int spirit;
	/** 精(成长值) */
	private @Getter @Setter int incr_energy;
	/** 气(成长值) */
	private @Getter @Setter int incr_breath;
	/** 神(成长值) */
	private @Getter @Setter int incr_spirit;

	/** 悟性 */
	private @Getter @Setter int savvy;
	/** 魅力 */
	private @Getter @Setter int glamour;
	/** 幸运 */
	private @Getter @Setter int luck;

	/** 巫术 */
	private @Getter @Setter int sorcery;
	/** 五行 */
	private @Getter @Setter int wuxing;
	/** 神打 */
	private @Getter @Setter int seance;
	/** 驱鬼 */
	private @Getter @Setter int exorcism;
	/** 符箓 */
	private @Getter @Setter int rune;
	/** 佛理 */
	private @Getter @Setter int buddha;
	/** 浩然 */
	private @Getter @Setter int confucian;
	/** 雷法 */
	private @Getter @Setter int thunder;
	/** 通幽 */
	private @Getter @Setter int demon;
	/** 御兽 */
	private @Getter @Setter int beast;
	/** 奇门 */
	private @Getter @Setter int qimen;

	/** 音律 */
	private @Getter @Setter int music;
	/** 棋弈 */
	private @Getter @Setter int chess;
	/** 书道 */
	private @Getter @Setter int calligraphy;
	/** 丹青 */
	private @Getter @Setter int paint;
	/** 医术 */
	private @Getter @Setter int leechcraft;
	/** 烹饪 */
	private @Getter @Setter int cook;
	/** 匠艺 */
	private @Getter @Setter int craft;
	/** 鉴定 */
	private @Getter @Setter int ident;
	/** 垂钓 */
	private @Getter @Setter int fishing;
	/** 狩猎 */
	private @Getter @Setter int hunting;
	/** 农桑 */
	private @Getter @Setter int farming;

}
