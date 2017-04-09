package com.leader.login.server.model;

import com.leader.core.server.model.MessageType;

/***
 * 网关服消息协议
 */
public class IMessageType implements MessageType {
	/** 心跳请求 */
	public static final int ReqInternalHeartbeatMessage = 1000;
	/** 服务器注册消息请求 */
	public static final int ReqRegisterServer = 1001;

	/** 网关服注册响应消息 */
	public static final int ResRegisterServer = 2001;
}
