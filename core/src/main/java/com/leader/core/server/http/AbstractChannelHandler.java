package com.leader.core.server.http;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

/**
 * AbstractChannelHandler
 * 
 * @author
 */
public abstract class AbstractChannelHandler implements ChannelHandler {

	/**
	 * 发送HTTP请求响应
	 * 
	 * @param ctx
	 * @param req
	 * @param version
	 * @param status
	 * @param result
	 */
	public void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, HttpVersion version,
			HttpResponseStatus status, String result) {
		ByteBuf bb = Unpooled.buffer();
		bb.writeBytes(result.getBytes(Charset.forName("UTF-8")));

		DefaultFullHttpResponse res = new DefaultFullHttpResponse(version, status, bb);

		if (res.status().code() != 200) {
			ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(), CharsetUtil.UTF_8);
			res.content().writeBytes(buf);
			buf.release();
		}
		res.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
		HttpUtil.setContentLength(res, res.content().readableBytes());
		ChannelFuture f = ctx.channel().writeAndFlush(res);
		if (!HttpUtil.isKeepAlive(req) || res.status().code() != 200) {
			f.addListener(ChannelFutureListener.CLOSE);
		}
	}

}
