package com.leader.game.data.container;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.leader.game.data.model.RoleData;
import com.leader.game.util.ExcelUtils;

import lombok.Getter;
import lombok.extern.log4j.Log4j;

@Component
@Log4j
public class RoleDataContainer {
	private @Getter List<RoleData> list;
	private @Getter Map<Integer, RoleData> map;

	@PostConstruct
	private void load() {
		try {
			list = ExcelUtils.load(RoleData.class, "./config/data/role_data.xls");
			map = new HashMap<>();
			for (RoleData data : list) {
				map.put(data.getId(), data);
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
