package com.leader.game.data.model;

import lombok.Getter;
import lombok.Setter;

public class SkillData {
	private @Getter @Setter int id;
	/** 名字 */
	private @Getter @Setter String name;
	/** 1天赋 2轻功 3心法 4普通技能 5羁绊技能 */
	private @Getter @Setter int type1;
	/** 1巫术 2五行 3神打 4驱鬼 5符箓 6佛理 7浩然 8雷法 9通幽 10御兽 11奇门 */
	private @Getter @Setter int type2;
	/** 0无 1音律 2棋弈 3书道 4丹青 5医术 6烹饪 7匠艺 8鉴定 9垂钓 10狩猎 11农桑 */
	private @Getter @Setter int type3;
	/** 1敌方单体 2敌方群体 3友方单体 4友方群体 5自身 */
	private @Getter @Setter int type5;

	/** 悟性要求 */
	private @Getter @Setter int savvy;
	/** 道德要求 */
	private @Getter @Setter int morality;
	/** 魅力要求 */
	private @Getter @Setter int glamour;
	/** 幸运要求 */
	private @Getter @Setter int luck;

	/** 施法材料(id:number|id:number|id:number) */
	private @Getter @Setter String material1;
	/** 灵契类型 */
	private @Getter @Setter int contract_type;
	/** 灵契id */
	private @Getter @Setter int contract_id;

	/** 射程 */
	private @Getter @Setter int range;
	/** 伤害 */
	private @Getter @Setter int damage;
	/** 属性(1水 2火 3雷 4风 5土) */
	private @Getter @Setter int property;
	/** 造成的异常状态(id) */
	private @Getter @Setter int debuff;
	/** 造成的异常状态几率 */
	private @Getter @Setter int debuff_chance;

	/** 升级所需经验 */
	private @Getter @Setter int exp;

}
