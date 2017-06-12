package com.leader.login.server.model;

import com.leader.core.server.model.MessageType;

/***
 * 网关服消息协议
 */
public class IMessageType implements MessageType {
	// [start] ServerInternalProtocol
	/** 网关服注册心跳消息 */
	public static final int ReqInternalHeartbeat = 1000;
	/** 服务器注册消息请求 */
	public static final int ReqRegisterServer = 1001;
	/** 更新玩家角色请求 */
	public static final int ReqUpdatePlayer = 1002;
	/** 验证玩家登录令牌请求 */
	public static final int ReqVerifyToken = 1003;

	/** 网关服注册响应消息 */
	public static final int ResRegisterServer = 2001;
	/** 验证玩家登录令牌响应 */
	public static final int ResVerifyToken = 2003;
	// [end]

	// [start] UserProtocol
	/** 用户登录请求消息 */
	public static final int ReqUserLogin = 1100;
	/** 用户注册请求消息 */
	public static final int ReqUserRegister = 1101;

	/** 用户登录响应消息 */
	public static final int ResUserLogin = 2100;
	/** 用户注册响应消息 */
	public static final int ResUserRegister = 2101;
	// [end]
}
