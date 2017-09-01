package com.leader.core.server.model;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.leader.core.util.TextKit;

public class DynamicMessageFactory {
	private static HashMap<String, Short> descriptors = new HashMap<String, Short>();
	private static Logger log = LoggerFactory.getLogger(DynamicMessageFactory.class);

	/**
	 * 获取descriptor
	 * 
	 * @param descriptor
	 * @return
	 */
	public static short getDescriptor(String descriptor) {
		descriptor = TextKit.replace(descriptor, "Message", "");
		if (!descriptors.containsKey(descriptor)) {
			log.error("未找到{}对应的消息!", descriptor);
		}
		return descriptors.get(descriptor);
	}

	public static void put(String descriptor, short type) {
		descriptors.put(descriptor, type);
	}

}
