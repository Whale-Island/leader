package com.leader.game.server.thread;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.Message.Builder;
import com.leader.core.server.TcpClient;
import com.leader.core.server.pool.LogicThreadPool;
import com.leader.game.protobuf.protocol.ServerInternalProtocol.ReqInternalHeartbeatMessage;
import com.leader.game.server.GameServer;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

/**
 * 注册游戏服线程
 * 
 * @auth
 *
 */
public class ConnectServerThread extends Thread {
	/** 服务器ip地址 */
	private String serverIP;
	/** 服务器端口 */
	private int serverPort;
	/** 线程运行标识 */
	private volatile boolean run = true;
	/** log */
	private Logger log = LoggerFactory.getLogger(getClass());
	/** tcpclient */
	private TcpClient tcpClient;
	/** channel */
	private Channel channel;
	/** listener */
	private List<ChannelFutureListener> listeners = new LinkedList<ChannelFutureListener>();
	/** 心跳listener */
	private ChannelFutureListener heartbeatListener = new ChannelFutureListener() {
		@Override
		public void operationComplete(ChannelFuture future) throws Exception {
			channel = future.channel();
			LogicThreadPool.threadpool.scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {
					ReqInternalHeartbeatMessage.Builder builder = ReqInternalHeartbeatMessage.newBuilder();
					builder.setOnline(GameServer.Intstance.getChannelGroup().size());
					channel.writeAndFlush(builder);
				}
			}, 10 * 1000, 10 * 1000, TimeUnit.MILLISECONDS);
		}
	};

	/**
	 * 
	 */
	public ConnectServerThread() {
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 */
	public ConnectServerThread(String name) {
		super(name);
	}

	/**
	 * @param serverIP
	 * @param serverPort
	 */
	public ConnectServerThread(String serverIP, int serverPort) {
		this.serverIP = serverIP;
		this.serverPort = serverPort;
	}

	/**
	 * @return the listeners
	 */
	public final List<ChannelFutureListener> getListeners() {
		return listeners;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		while (run) {
			try {
				tcpClient = new TcpClient(serverIP, serverPort);
				tcpClient.getListeners().add(heartbeatListener);
				if (!listeners.isEmpty()) {
					tcpClient.getListeners().addAll(listeners);
				}
				tcpClient.start();
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					log.error(e1.getMessage(), e1);
				}
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		log.info("RegisterThread End.");
	}

	/**
	 * 停止线程方法
	 */
	public void stop0() {
		run = false;
		tcpClient.stop();
		channel = null;
	}

	/**
	 * 发送消息
	 * 
	 * @param builder
	 */
	public void sendMessage(Builder builder) {
		if (!run) {
			return;
		}
		if (channel != null && channel.isActive()) {
			channel.writeAndFlush(builder);
		}
	}
}
