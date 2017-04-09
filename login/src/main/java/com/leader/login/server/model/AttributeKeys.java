package com.leader.login.server.model;

import com.leader.login.user.model.User;

import io.netty.util.AttributeKey;

/**
 * netty channel变量keys
 * 
 * @author
 */
public interface AttributeKeys {
	/** 服务器id */
	AttributeKey<Integer> SERVER_ID = AttributeKey.newInstance("SERVER_ID");

	/** 服务器id */
	AttributeKey<User> USER = AttributeKey.newInstance("USER");
}
