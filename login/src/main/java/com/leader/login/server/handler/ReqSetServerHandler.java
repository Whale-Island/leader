package com.leader.login.server.handler;

import java.util.Map;

import com.leader.core.server.http.AbstractChannelHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/***
 * 设置服务器信息
 */
public class ReqSetServerHandler extends AbstractChannelHandler {

	@Override
	public void channel(ChannelHandlerContext ctx, FullHttpRequest req, Map<String, String> map) {

	}

}
