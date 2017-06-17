package com.leader.login.server.model;

import com.leader.core.server.model.IMessageType;

/***
 * 网关服消息协议
 */
public class MessageType implements IMessageType {
	// [start] ServerInternalProtocol
	/** 网关服注册心跳消息 */
	public static final short ReqInternalHeartbeat = 100;
	/** 服务器注册消息请求 */
	public static final short ReqRegisterServer = 101;
	/** 更新玩家角色请求 */
	public static final short ReqUpdatePlayer = 102;
	/** 验证玩家登录令牌请求 */
	public static final short ReqVerifyToken = 103;

	/** 网关服注册响应消息 */
	public static final short ResRegisterServer = 201;
	/** 验证玩家登录令牌响应 */
	public static final short ResVerifyToken = 203;
	// [end]

	// [start] UserProtocol
	/** 用户登录请求消息 */
	public static final short ReqUserLogin = 110;
	/** 用户注册请求消息 */
	public static final short ReqUserRegister = 111;

	/** 用户登录响应消息 */
	public static final short ResUserLogin = 210;
	/** 用户注册响应消息 */
	public static final short ResUserRegister = 211;
	// [end]
}
