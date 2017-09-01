package com.leader.game.server.model;

import com.leader.core.server.model.IMessageType;

/***
 * 游戏服消息协议
 */
public class MessageType implements IMessageType {
	// [start] ServerInternalProtocol
	/** 内部服务器心跳请求 */
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

	// [start] SyncProtocol
	/** 同步请求 */
	public static final short ReqSync = 999;
	// [end]

	// [start] Player
	/** 心跳请求 */
	public static final short ReqHeartbeat = 1000;
	/** 登录请求 */
	public static final short ReqLogin = 1101;
	/** 注册请求 */
	public static final short ReqRegister = 1102;
	/** 获取随机名称请求 */
	public static final short ReqRandomName = 1103;

	/** 登录响应 */
	public static final short ResLogin = 2101;
	/** 注册响应 */
	public static final short ResRegister = 2102;
	/** 获取随机名称响应 */
	public static final short ResRandomName = 2103;
	/** 属性变化通知响应 */
	public static final short ResPropertyChange = 2104;
	// [end]

	// [start] Sect
	/** 创建门派请求 */
	public static final short ReqCreatSect = 1200;
	public static final short ReqRandomSectName = 1201;

	/** 创建门派响应 */
	public static final short ResCreatSect = 2200;
	public static final short ResRandomSectName = 2201;
	// [end]
}
