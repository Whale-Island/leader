package com.leader.game.role.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.leader.core.db.GameEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/***
 * 角色卡牌实体
 * 
 * @author siestacat
 *
 */
@Entity
@Table(name = "Role")
@NamedQueries({ // [start]
		@NamedQuery(name = "Role.findBySectId", query = "SELECT r FROM Role r WHERE r.sectId = ?1"),
		@NamedQuery(name = "Role.findById", query = "SELECT r FROM Role r WHERE r.id = ?1") // [end]
})
@ToString
public class Role implements GameEntity {
	private static final long serialVersionUID = 2217982323371130763L;
	@Id
	@Column(unique = true, nullable = false)
	private @Getter @Setter long id;
	/** 玩家id */
	private @Getter @Setter long playerId;
	/** 门派id */
	private @Getter @Setter long sectId;
	/** 模版id */
	private @Getter @Setter int modelId;
	/** 昵称 */
	private @Getter @Setter String name;
	/** 等级 */
	private @Getter @Setter int level;
	/** 阶层 */
	private @Getter @Setter int clazz;
	/** 称号 */
	private @Getter @Setter String title;
	/** 性格 */
	private @Getter @Setter int nature;
	/** 体力 */
	private @Getter @Setter int stamina;

	/** 关系表 业务层操作 (key->modelId) */
	private @Getter @Transient Map<Integer, Integer> relationMap = new HashMap<>();

	/** 关系表(id:num|id:num) 数据库保存用字段，业务中不进行操作 */
	private @Setter String relations;

	/** 精 */
	private @Getter @Setter int energy;
	/** 气 */
	private @Getter @Setter int breath;
	/** 神 */
	private @Getter @Setter int spirit;

	/** 职业名称 */
	private @Getter @Setter String profession;
	/** 资质(id) */
	private @Getter @Setter int aptitude;
	/** 天赋技能(id) */
	private @Getter @Setter int talent;
	/** 喜欢的事物(id,id,id) */
	private @Getter @Setter int[] favorite;

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

	/** 悟性 */
	private @Getter @Setter int savvy;
	/** 道德 */
	private @Getter @Setter int morality;
	/** 魅力 */
	private @Getter @Setter int glamour;
	/** 幸运 */
	private @Getter @Setter int luck;

	public Role() {
		// 填充关系表
		String[] relationArray = relations.split("\\|");
		for (String relation : relationArray) {
			String[] single = relation.split(":");
			relationMap.put(Integer.valueOf(single[0]), Integer.valueOf(single[1]));
		}

	}

	/** 保存时读取用，业务层操作请操作{@link #relationMap} */
	public String getRelations() {
		return relationMap.toString();
	}

	/** 获取技艺 */
	public int getArt(int type) {
		switch (type) {
		case 1:
			return music;
		case 2:
			return chess;
		case 3:
			return calligraphy;
		case 4:
			return paint;
		case 5:
			return leechcraft;
		case 6:
			return cook;
		case 7:
			return craft;
		case 8:
			return ident;
		case 9:
			return fishing;
		case 10:
			return hunting;
		case 11:
			return farming;

		default:
			return 0;
		}
	}

	/** 获取法术 */
	public int getMagic(int type) {
		switch (type) {
		case 1:
			return sorcery;
		case 2:
			return wuxing;
		case 3:
			return seance;
		case 4:
			return exorcism;
		case 5:
			return rune;
		case 6:
			return buddha;
		case 7:
			return confucian;
		case 8:
			return thunder;
		case 9:
			return demon;
		case 10:
			return beast;
		case 11:
			return qimen;

		default:
			return 0;
		}
	}
}
