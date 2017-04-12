package com.leader.game.role;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.leader.core.db.CommonDao;
import com.leader.core.server.model.ShutdownListener;
import com.leader.game.protobuf.protocol.RoleProtocol.RoleInfo;
import com.leader.game.role.dao.RoleDao;
import com.leader.game.role.model.Role;

public enum RoleManager implements ShutdownListener {
	Intstance;
	@Autowired
	RoleDao dao;
	@Autowired
	CommonDao commonDao;

	/** 创建门派时初始化弟子 */
	public List<Role> allotRole(String name, int sex) {
		return null;
	}

	/** 根据门派id获取弟子列表 */
	public List<Role> findRoles(long sectId) {
		return dao.findBySectId(sectId);
	}

	/** role装进protobuf */
	public RoleInfo.Builder packRole(Role role) {
		RoleInfo.Builder builder = RoleInfo.newBuilder();
		builder.setId(role.getId());
		builder.setPlayerId(role.getPlayerId());
		builder.setSectId(role.getSectId());
		builder.setModelId(role.getModelId());
		builder.setName(role.getName());
		builder.setLevel(role.getLevel());
		builder.setClazz(role.getClazz());
		builder.setTitle(role.getTitle());
		return builder;

	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub

	}
}
