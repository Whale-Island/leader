package com.leader.game.server.manager;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.beans.factory.annotation.Autowired;

import com.leader.core.db.CommonDao;
import com.leader.core.server.model.ShutdownListener;
import com.leader.game.server.model.GlobalKey;
import com.leader.game.server.model.GlobalVariables;

public class GlobalManager implements ShutdownListener {

	@Autowired
	private CommonDao commonDao;
	private ConcurrentMap<GlobalKey, GlobalVariables> globalVariablesMap = new ConcurrentHashMap<GlobalKey, GlobalVariables>();

	private GlobalManager() {
	}

	private static class SigletonHolder {
		static final GlobalManager INSTANCE = new GlobalManager();
	}

	public static GlobalManager getInstance() {
		return SigletonHolder.INSTANCE;
	}

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
