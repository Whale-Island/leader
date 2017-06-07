package com.leader.game.sect.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.leader.core.db.GameEntity;
import com.leader.game.role.RoleManager;
import com.leader.game.role.model.Role;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "sect")
@NamedQueries({ // [start]
		@NamedQuery(name = "Sect.findById", query = "SELECT r FROM Sect r WHERE r.id = ?1"),
		@NamedQuery(name = "Sect.findByPlayerId", query = "SELECT r FROM Sect r WHERE r.playerId = ?1"),
		@NamedQuery(name = "Sect.findByName", query = "SELECT r FROM Sect r WHERE r.name = :name") // [end]
})
@NamedNativeQueries({ // [start] BriefInfo
		@NamedNativeQuery(name = "Sect.getAllName", query = "SELECT name FROM Sect", resultSetMapping = "Sect.name") }) // [end]
@SqlResultSetMappings({ // [start]
		@SqlResultSetMapping(name = "Sect.name", columns = { @ColumnResult(name = "name", type = String.class) }) // [end]
})
@ToString
public class Sect implements GameEntity {

	private static final long serialVersionUID = -6742581128023001813L;

	@Id
	@Column(unique = true, nullable = false)
	private @Getter @Setter long id;
	/** 玩家id */
	private @Getter @Setter long playerId;
	/** 玩家帐号 */
	private @Getter @Setter String username;
	/** 门派名称 */
	@Column(unique = true, nullable = false)
	private @Getter @Setter String name;
	/** 等级 */
	private @Getter @Setter int level;
	/** 经验 */
	private @Getter @Setter long exp;
	/** 威望 */
	private @Getter @Setter int prestige;
	/** 金币 */
	private AtomicLong gold;
	/** 钻石 */
	private AtomicInteger diamond;
	/** 仓库ID */
	private @Getter @Setter int storageId;
	/** 弟子们 */
	private @Getter @Setter @Transient List<Role> roles = new ArrayList<>();
	/** 弟子们(key=模版id) */
	private @Getter @Setter @Transient Map<Integer, Role> roleMap = new HashMap<>();

	public Sect() {
		if (id != 0)
			roles.addAll(RoleManager.getInstance().findRoles(id));
		for (Role role : roles) {
			roleMap.put(role.getModelId(), role);
		}

	}

	/** 增加金币 */
	public long incrGold(long num) {

		gold.addAndGet(num);
		return gold.get();
	}

	/** 增加钻石 */
	public long incrDiamond(long num) {
		return diamond.get();
	}

	public long getGold() {
		return gold.get();
	}

	public void setGold(long gold) {
		this.gold.set(gold);
	}

	public int getDiamond() {
		return diamond.get();
	}

	public void setDiamond(int diamond) {
		this.diamond.set(diamond);
	}

}
