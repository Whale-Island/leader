package com.leader.gateway.server.model;

import io.netty.util.AttributeKey;

/**
 * netty channel变量keys
 * 
 * @author
 */
public interface AttributeKeys {
	/** 服务器id */
	AttributeKey<Integer> SERVER_ID = AttributeKey.newInstance("SERVER_ID");
}
