package com.leader.core.server.http;

import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpResponseStatus.NOT_FOUND;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.leader.core.util.TextKit;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.MixedAttribute;
import io.netty.util.CharsetUtil;

/**
 * HTTP SERVER消息處理器
 * 
 * @author Administrator
 */
public final class HttpServerHandler extends SimpleChannelInboundHandler<Object> {

	/** 日志记录 */
	private static final Logger log = LoggerFactory.getLogger(HttpServerHandler.class);
	public static final String GET = "GET";
	public static final String POST = "POST";

	/** 通道处理器列表 */
	public ConcurrentMap<String, ChannelHandler> handlerMap = new ConcurrentHashMap<String, ChannelHandler>();

	public HttpServerHandler() {
	}

	/** 设置指定文件名的通道处理器 */
	public void registerHandler(String uri, ChannelHandler handler) {
		handlerMap.putIfAbsent(uri, handler);
	}

	@Override
	public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof HttpRequest) {
			handleRequest(ctx, (FullHttpRequest) msg);
		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.error(cause.getMessage(), cause);
		ctx.close();
	}

	/**
	 * 处理不同的HTTP请求
	 */
	public void handleRequest(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception {
		if (!req.decoderResult().isSuccess()) {// Handle a bad request.
			FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, BAD_REQUEST);
			sendHttpResponse(ctx, req, res);
			return;
		}
		if (req.uri().equals("/favicon.ico")) {
			FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, NOT_FOUND);
			sendHttpResponse(ctx, req, res);
			return;
		}
		String method = req.method().name();
		if (POST.equalsIgnoreCase(method)) {
			doPost(ctx, req);
		} else if (GET.equalsIgnoreCase(method)) {
			doGet(ctx, req);
		}
	}

	/** GET请求处理方法 */
	public void doGet(ChannelHandlerContext ctx, FullHttpRequest req) {
		Map<String, String> map = new HashMap<String, String>();
		parseQueryString(getFileQuery(req.uri()), map);
		handler(ctx, req, map);
	}

	/** POST请求处理方法 */
	public void doPost(ChannelHandlerContext ctx, FullHttpRequest req) {
		Map<String, String> params = new HashMap<String, String>();
		ByteBuf from = req.content();
		if (from.readableBytes() > 0) {
			ByteBuf to = Unpooled.buffer(from.readableBytes());
			from.getBytes(0, to);
			String bodyContent = new String(to.array(), Charset.forName("UTF-8"));
			params.put("bodyContent", bodyContent);
		}
		parseQueryString(getFileQuery(req.uri()), params);
		HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(req);
		List<InterfaceHttpData> list = decoder.getBodyHttpDatas();
		for (InterfaceHttpData data : list) {
			if (data instanceof MixedAttribute) {
				MixedAttribute attribute = (MixedAttribute) data;
				try {
					params.put(attribute.getName(), attribute.getValue());
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
		}
		decoder.destroy();
		handler(ctx, req, params);
	}

	/** 消息分发处理方法 */
	public void handler(ChannelHandlerContext ctx, FullHttpRequest req, Map<String, String> map) {
		String uri = req.uri();
		int index = uri.indexOf('?');
		if (index > 0) {
			uri = uri.substring(0, index);
		}
		ChannelHandler handler = handlerMap.get(uri);
		if (handler != null) {
			try {
				handler.channel(ctx, req, map);
			} catch (Throwable t) {
				log.error(t.getMessage(), t);
			}
			return;
		} else {
			log.error("No Handler Found,URI = " + req.uri());
			log.error("FullHttpRequest = " + req.toString());
		}
		ByteBuf bb = Unpooled.buffer();
		bb.writeBytes("Welcome to nginx!".getBytes(Charset.forName("UTF-8")));
		FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, OK, bb);
		sendHttpResponse(ctx, req, res);
	}

	/** 用指定的字符编码分析请求的字符串，返回键值对表 */
	public void parseQueryString(String s, Map<String, String> map) {
		if (s == null || s.length() == 0) {
			return;
		}
		String[] strs = TextKit.split(s, '&');
		int j;
		for (int i = 0; i < strs.length; i++) {
			s = strs[i];
			j = s.indexOf('=');
			if (j < 0)
				continue;
			map.put(s.substring(0, j), s.substring(j + 1));
		}
	}

	/** 发送响应 */
	public void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse res) {
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

	/** 获得文件名中的请求字段 */
	public static String getFileQuery(String file) {
		int index1 = file.indexOf('?');
		if (index1 < 0) {
			return null;
		}
		int index2 = file.indexOf('#');
		if (index2 < index1) {
			return file.substring(index1 + 1);
		}
		return file.substring(index1 + 1, index2);
	}
}
