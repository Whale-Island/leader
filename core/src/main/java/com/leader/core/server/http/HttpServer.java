/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.leader.core.server.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * HTTP SERVER
 * 
 * @author
 */
public class HttpServer {

	/** 日志 */
	private static final Logger logger = LoggerFactory.getLogger(HttpServer.class);
	/** HTTP服务器端口 */
	private final int port;
	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;
	private ChannelHandler channelHandler;

	/**
	 * constructor
	 * 
	 * @param port
	 * @param channelHandler
	 */
	public HttpServer(int port, ChannelHandler channelHandler) {
		if (port <= 0 || channelHandler == null) {
			throw new IllegalArgumentException("param is error,port = " + port + ",channelHandler = " + channelHandler);
		}
		this.channelHandler = channelHandler;
		this.port = port;
	}

	/**
	 * 开启HTTP server
	 */
	public void start() {
		bossGroup = new NioEventLoopGroup(1);
		workerGroup = new NioEventLoopGroup(2);
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(channelHandler);
			Channel ch = b.bind(port).sync().channel();
			logger.info("Web server started at port " + port + '.');
			ch.closeFuture().sync();
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	/**
	 * 停止Http server
	 */
	public void stop() {
		bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();
	}
}
