package com.leader.game.server.model;

import com.leader.core.server.model.MessageType;

/***
 * 游戏服消息协议
 */
public class IMessageType implements MessageType {
	// [start] ServerInternalProtocol
	/** 内部服务器心跳请求 */
	public static final int ReqInternalHeartbeat = 1000;
	/** 服务器注册消息请求 */
	public static final int ReqRegisterServer = 1001;
	/** 更新玩家角色请求 */
	public static final int ReqUpdatePlayer = 1002;

	/** 网关服注册响应消息 */
	public static final int ResRegisterServer = 2001;
	// [end]

	// [start] SyncProtocol
	/** 同步请求 */
	public static final int ReqSync = 101;
	// [end]

	// [start] Player
	/** 心跳请求 */
	public static final int ReqHeartbeat = 10000;
	/** 登录请求 */
	public static final int ReqLogin = 11001;
	/** 注册请求 */
	public static final int ReqRegister = 11002;
	/** 获取随机名称请求 */
	public static final int ReqRandomName = 11003;

	/** 登录响应 */
	public static final int ResLogin = 21001;
	/** 注册响应 */
	public static final int ResRegister = 21002;
	/** 获取随机名称响应 */
	public static final int ResRandomName = 21003;
	/** 属性变化通知响应 */
	public static final int ResPropertyChange = 21004;
	// [end]

	// [start] Sect
	/** 创建门派请求 */
	public static final int ReqCreatSect = 12000;
	public static final int ReqRandomSectName = 12001;

	/** 创建门派响应 */
	public static final int ResCreatSect = 22000;
	public static final int ResRandomSectName = 22001;
	// [end]
}
