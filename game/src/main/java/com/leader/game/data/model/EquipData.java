package com.leader.game.data.model;

import lombok.Getter;
import lombok.Setter;

public class EquipData {
	private @Getter @Setter int id;
	/** 名字 */
	private @Getter @Setter String name;
	/** 描述 */
	private @Getter @Setter String describe;
	/** 穿戴位置 1武器 2身体 3头部 4颈部 5手部 6脚部 */
	private @Getter @Setter int position;
	/** 品阶 */
	private @Getter @Setter int grade;

	/** 套装(id) */
	private @Getter @Setter int suit;
	/** 属性(0无属性 1水 2火 3雷 4风 5土) */
	private @Getter @Setter int property;
	/** 造成的异常状态(id) */
	private @Getter @Setter int debuff;
	/** 造成的异常状态几率 */
	private @Getter @Setter int debuff_PR;

	/** 增加精 */
	private @Getter @Setter int energy;
	/** 增加气 */
	private @Getter @Setter int breath;
	/** 增加神 */
	private @Getter @Setter int spirit;
	/** 增加悟性 */
	private @Getter @Setter int savvy;
	/** 增加道德 */
	private @Getter @Setter int morality;
	/** 增加魅力 */
	private @Getter @Setter int glamour;
	/** 增加幸运 */
	private @Getter @Setter int luck;

	/** 增加攻击力 */
	private @Getter @Setter int damage;
	/** 增加护甲 */
	private @Getter @Setter int armor;
	/** 增加血上限 */
	private @Getter @Setter int blood;
	/** 增加速度 */
	private @Getter @Setter int speed;
	/** 增加法力上限 */
	private @Getter @Setter int mana;
	/** 增加法力回复速度 */
	private @Getter @Setter int mana_recov;
	/** 增加暴击伤害 */
	private @Getter @Setter int crit;
	/** 增加暴击概率 */
	private @Getter @Setter int crit_PR;
	/** 增加破甲概率 */
	private @Getter @Setter int disregard;

	/** 悟性要求 */
	private @Getter @Setter int c_savvy;
	/** 道德要求 */
	private @Getter @Setter int c_morality;
	/** 魅力要求 */
	private @Getter @Setter int c_glamour;
	/** 幸运要求 */
	private @Getter @Setter int c_luck;
	/** 法术要求(type:number|type:number|type:number) */
	private @Getter @Setter String c_magic;
	/** 技艺要求(type:number|type:number|type:number) */
	private @Getter @Setter String c_art;

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
