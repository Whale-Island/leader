package com.leader.core.server.model;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DynamicMessageFactory {
	private static HashMap<String, Integer> descriptors = new HashMap<String, Integer>();
	private static Logger log = LoggerFactory.getLogger(DynamicMessageFactory.class);

	/**
	 * 获取descriptor
	 * 
	 * @param descriptor
	 * @return
	 */
	public static int getDescriptor(String descriptor) {
		if (!descriptors.containsKey(descriptor)) {
			log.error("未找到对应的消息!");
		}
		return descriptors.get(descriptor);
	}

	public static void put(String descriptor, int type) {
		descriptors.put(descriptor, type);
	}

}
