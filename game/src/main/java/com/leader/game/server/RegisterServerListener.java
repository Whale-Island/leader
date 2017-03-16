package com.leader.game.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.leader.game.protobuf.protocol.ServerInternalProtocol.ReqRegisterServerMessage;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

/**
 * 注册服务器监听器
 * 
 * @author
 *
 */
public class RegisterServerListener implements ChannelFutureListener {

	private Logger log = LoggerFactory.getLogger(getClass());
	private String serverIP;
	private String serverName;
	private int serverPort;
	private int serverId;

	/**
	 * @param serverIP
	 * @param serverName
	 * @param serverPort
	 * @param serverId
	 */
	public RegisterServerListener(String serverIP, String serverName, int serverPort, int serverId) {
		this.serverIP = serverIP;
		this.serverName = serverName;
		this.serverPort = serverPort;
		this.serverId = serverId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.netty.util.concurrent.GenericFutureListener#operationComplete(io.netty
	 * .util.concurrent.Future)
	 */
	@Override
	public void operationComplete(ChannelFuture future) throws Exception {
		Channel channel = future.channel();
		if (!channel.isActive()) {
			log.error("channel is not active.");
		}
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				ReqRegisterServerMessage.Builder builder = ReqRegisterServerMessage.newBuilder();
				builder.setIp(serverIP);
				builder.setName(serverName);
				builder.setPort(serverPort);
				builder.setServerId(serverId);
				channel.writeAndFlush(builder);
			}
		});
		thread.start();
	}

}
