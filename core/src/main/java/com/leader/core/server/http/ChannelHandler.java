package com.leader.core.server.http;

import java.util.Map;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * ChannelHandler
 * 
 * @author
 */
public interface ChannelHandler {
	void channel(ChannelHandlerContext ctx, FullHttpRequest req, Map<String, String> map);
}
