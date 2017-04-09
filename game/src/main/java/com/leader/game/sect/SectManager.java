package com.leader.game.sect;

import org.springframework.beans.factory.annotation.Autowired;

import com.leader.core.db.CommonDao;
import com.leader.core.server.model.ShutdownListener;
import com.leader.game.sect.dao.SectDao;
import com.leader.game.sect.model.Sect;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/** 门派管理 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public enum SectManager implements ShutdownListener {
	Intstance;
	@Autowired
	SectDao dao;
	@Autowired
	CommonDao commonDao;

	/** 创建门派 */
	public Sect creatSect(String name) {
		Sect sect = new Sect();
		commonDao.store(sect);
		return sect;
	}

	/** 加载门派 */
	public Sect loadSect(long id) {
		Sect sect = dao.findByPlayerID(id);
		if (sect == null)
			log.error("未找到玩家id为{}的挂载门派！", id);
		return sect;
	}

	@Override
	public void shutdown() {
		log.debug("门派管理器已关闭!");
	}

}
