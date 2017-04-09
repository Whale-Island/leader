package com.leader.game.player.model;

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
import com.leader.game.role.model.Role;
import com.leader.game.sect.model.Sect;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "player")
@NamedQueries({ // start
		@NamedQuery(name = "Player.findAll", query = "SELECT r FROM Player r"),
		@NamedQuery(name = "Player.findPlayerById", query = "SELECT r FROM Player r WHERE r.id = ?1"),
		@NamedQuery(name = "Player.findPlayerByName", query = "SELECT r FROM Player r WHERE r.username = :username") // end
})
@NamedNativeQueries({ // start BriefInfo
		@NamedNativeQuery(name = "Player.getAllNickname", query = "SELECT nickname FROM Player", resultSetMapping = "Player.nickname"), })
@SqlResultSetMappings({ // start
		@SqlResultSetMapping(name = "Player.nickname", columns = {
				@ColumnResult(name = "nickname", type = String.class) }) // end
})
@ToString
public class Player implements GameEntity {
	private static final long serialVersionUID = 1184515259071679517L;

	@Id
	@Column(unique = true, nullable = false)
	private @Getter @Setter long id;
	/** 帐号(冗余字段) */
	private @Getter @Setter String username;
	/** 角色id */
	private @Getter @Setter long roleId;
	/** 昵称 */
	@Column(unique = true, nullable = false)
	private @Getter @Setter String nickname;
	/** 挂载的角色 */
	private @Getter @Setter @Transient Role role;
	/** 门派id */
	private @Getter @Setter int sectId;
	/** 门派名 */
	@Column(unique = true, nullable = false)
	private @Getter @Setter String sectName;
	/** 挂载的门派 */
	private @Getter @Setter @Transient Sect sect;
	/** 最后一次在线时间 */
	private @Getter @Setter long lastOnlineTime;
	/** 上次登录时间 */
	private @Getter @Setter long preLoginTime;
	/** 平台渠道 */
	private @Getter @Setter int channel;
	/** 设备号 */
	private @Getter @Setter String deviceId;
	/** 状态 */
	private @Getter @Setter @Transient volatile int state;
	/** 1.在线 2.离线 */
	public static final int ONLINE = 1, OFFLINE = 2, SAVE = 3;

	public final boolean isOnline() {
		return state == ONLINE;
	}

}
