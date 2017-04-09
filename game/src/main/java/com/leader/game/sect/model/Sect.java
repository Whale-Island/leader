package com.leader.game.sect.model;

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

import com.leader.core.db.GameEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "sect")
@NamedQueries({ // [start]
		@NamedQuery(name = "Sect.findAll", query = "SELECT r FROM Sect r"),
		@NamedQuery(name = "Sect.findById", query = "SELECT r FROM Sect r WHERE r.id = ?1"),
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
	private @Getter @Setter int id;
	/** 门派名称 */
	private @Getter @Setter String name;
	/** 等级 */
	private @Getter @Setter int level;
	/** 经验 */
	private @Getter @Setter long exp;
	/** 人数 */
	private @Getter @Setter int num;
	/** 威望 */
	private @Getter @Setter int prestige;
	/** 金币 */
	private AtomicLong gold;
	/** 钻石 */
	private AtomicInteger diamond;

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
