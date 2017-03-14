/**
 * 
 */
package com.leader.game.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.leader.core.server.http.HttpServer;

import io.netty.channel.ChannelHandler;

/**
 * HTTP线程
 * 
 * @author
 */
public class HttpServerThread extends Thread {

	/**
	 * httpserver
	 */
	private HttpServer httpServer;

	/**
	 * 端口
	 */
	private int port;

	/**
	 * ChannelHandler
	 */
	private ChannelHandler channelHandler;

	/**
	 * 日志
	 */
	private Logger log = LoggerFactory.getLogger(HttpServerThread.class);

	/**
	 * 
	 */
	public HttpServerThread(String name, int port, ChannelHandler channelHandler) {
		super(name);
		this.port = port;
		this.channelHandler = channelHandler;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		httpServer = new HttpServer(this.port, this.channelHandler);
		httpServer.start();
	}

	/**
	 * {@inheritDoc}
	 */
	public void stop0() {
		try {
			httpServer.stop();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

}
