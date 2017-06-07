package com.leader.game.server.manager;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.beans.factory.annotation.Autowired;

import com.leader.core.db.CommonDao;
import com.leader.core.server.model.ShutdownListener;
import com.leader.game.server.model.GlobalKey;
import com.leader.game.server.model.GlobalVariables;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/** 全局变量管理 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GlobalManager implements ShutdownListener {

	private static class SigletonHolder {
		static final GlobalManager INSTANCE = new GlobalManager();
	}

	public static GlobalManager getInstance() {
		return SigletonHolder.INSTANCE;
	}

	@Autowired
	private CommonDao commonDao;
	private ConcurrentMap<GlobalKey, GlobalVariables> globalVariablesMap = new ConcurrentHashMap<GlobalKey, GlobalVariables>();

	/**
	 * @return the map
	 */
	public final ConcurrentMap<GlobalKey, GlobalVariables> getMap() {
		return globalVariablesMap;
	}

	public GlobalVariables getValue(GlobalKey key) {
		return globalVariablesMap.get(key);
	}

	public void putValue(GlobalVariables globalVariables) {
		globalVariablesMap.put(globalVariables.getKey(), globalVariables);
	}

	public void init() {
		List<GlobalVariables> variables = commonDao.listEntity("GlobalVariables.findAll", GlobalVariables.class);
		if (variables != null && !variables.isEmpty()) {
			for (GlobalVariables globalVariables : variables) {
				globalVariablesMap.put(globalVariables.getKey(), globalVariables);
			}
		}
	}

	@Override
	public void shutdown() {
		commonDao.batchUpdateCollection(globalVariablesMap.values());
	}

}
