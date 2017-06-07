package com.leader.login.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.leader.core.db.GameEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "role")
@NamedQueries({ // start
		@NamedQuery(name = "Role.findRoleByName", query = "SELECT r FROM Role r WHERE "
				+ "r.username = :username order by serverId,level"),
		@NamedQuery(name = "Role.findRole", query = "SELECT r FROM Role r WHERE "
				+ "r.username = :username and r.serverId = :serverId") // end
})
public class Role implements GameEntity {
	private static final long serialVersionUID = -980826933743988382L;

	@Id
	@Column(unique = true, nullable = false)
	private @Getter @Setter long id;
	/** 帐号 */
	private @Getter @Setter String username;
	/** 昵称 */
	private @Getter @Setter String nickname;
	/** 性别 */
	private @Getter @Setter short sex;
	/** 等级 */
	private @Getter @Setter int level;
	/** 所在服务器名称 */
	private @Getter @Setter String servername;
	/** 所在服务器编号 */
	private @Getter @Setter int serverId;

}