package com.leader.game.data.container;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.leader.game.data.model.RoleEventData;
import com.leader.game.util.ExcelUtils;

import lombok.Getter;
import lombok.extern.log4j.Log4j;

@Component
@Log4j
public class RoleEventDataContainer {
	private @Getter Map<Integer, RoleEventData> map;
	private @Getter Map<Integer, List<RoleEventData>> map2;

	@PostConstruct
	private void load() {
		try {
			List<RoleEventData> list = ExcelUtils.load(RoleEventData.class, "./config/data/role_event_data.xls");
			map = new HashMap<>();
			for (RoleEventData data : list) {
				map.put(data.getId(), data);
				List<RoleEventData> roleEventDatas = map2.get(data.getRoleId());
				if (roleEventDatas == null) {
					roleEventDatas = new ArrayList<RoleEventData>();
					map2.put(data.getRoleId(), roleEventDatas);
				}
				roleEventDatas.add(data);
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
