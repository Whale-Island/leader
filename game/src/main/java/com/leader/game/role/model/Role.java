package com.leader.game.role.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

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
@Table(name = "role")
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
}
