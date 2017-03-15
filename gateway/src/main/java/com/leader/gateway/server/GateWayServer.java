package com.leader.gateway.server;

import java.net.InetSocketAddress;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.net.ssl.SSLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import com.leader.core.server.HttpServerThread;
import com.leader.core.server.TcpServer;
import com.leader.core.server.model.ShutdownListener;
import com.leader.core.server.pool.LogicThreadPool;
import com.leader.core.util.TextKit;
import com.leader.gateway.protobuf.protocol.GatewayProtocol.ReqRegisterServerMessage;
import com.leader.gateway.protobuf.protocol.GatewayProtocol.ResRegisterServerMessage;
import com.leader.gateway.protobuf.protocol.GatewayProtocol.ResServerListMessage;
import com.leader.gateway.protobuf.protocol.GatewayProtocol.ServerInfo;
import com.leader.gateway.server.model.AttributeKeys;
import com.leader.gateway.server.model.Server;
import com.leader.gateway.server.model.ServerChannelGroup;
import com.leader.gateway.server.model.ServerState;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.util.Attribute;
import io.netty.util.concurrent.GlobalEventExecutor;

public class GateWayServer {
	/** 日志 */
	private Logger logger = LoggerFactory.getLogger(GateWayServer.class);
	private ConcurrentMap<Integer, Server> servers = new ConcurrentHashMap<Integer, Server>();
	private ServerChannelGroup channelGroup = new ServerChannelGroup(GlobalEventExecutor.INSTANCE);
	@Value("${server_port}")
	private int serverPort;
	@Value("${server_name}")
	private String serverName;
	@Value("${http_port}")
	private int httpPort;
	/** 关服监听列表 */
	private List<ShutdownListener> listeners;
	/** http */
	private HttpServerThread httpServerThread;

	private GateWayServer() {
	}

	private static class SigletonHolder {
		static final GateWayServer INSTANCE = new GateWayServer();
	}

	public static GateWayServer getInstance() {
		return SigletonHolder.INSTANCE;
	}

	/** 网络连接关闭监听器 */
	private final ChannelFutureListener channelCloseListener = new ChannelFutureListener() {
		@Override
		public void operationComplete(ChannelFuture future) throws Exception {
			Channel channel = future.channel();
			String ip = "";
			InetSocketAddress address = (InetSocketAddress) channel.remoteAddress();
			if (address != null) {
				ip = address.toString();
				ip = TextKit.replaceAll(ip, "/", "");
			}
			logger.info("remote host " + ip + " is closed.");
			Attribute<Integer> attribute = channel.attr(AttributeKeys.SERVER_ID);
			Integer serverId = attribute.get();
			if (serverId == null) {
				return;
			}
			Server server = servers.get(serverId);
			if (server != null) {
				server.setState(ServerState.STOP);
			}
		}
	};

	/**
	 * 启动读取上下文环境
	 * 
	 * @return
	 * @throws CertificateException
	 * @throws SSLException
	 */
	public void run(AbstractApplicationContext applicationContext) throws SSLException, CertificateException {
		TcpServer tcpServer = new TcpServer(serverPort);
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				LogicThreadPool.shutdown();
				try {
					ThreadPoolTaskScheduler scheduler = applicationContext.getBean("TaskScheduler",
							ThreadPoolTaskScheduler.class);
					if (scheduler != null) {
						scheduler.shutdown();
					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
				try {
					if (httpServerThread != null) {
						httpServerThread.stop0();
					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
				try {
					tcpServer.stop();
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
				stop();
				try {
					applicationContext.close();
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
				logger.info(serverName + " stoped.");
			}
		}));
		logger.info(serverName + " started.");
		tcpServer.start();
	}

	/** 游戏服务器注册 */
	public void register(Channel channel, ReqRegisterServerMessage message) {
		int serverId = message.getServerId();
		Channel oldChannel = channelGroup.getChannel(serverId);
		if (oldChannel != null) {
			ChannelFuture future = oldChannel.close();
			int times = 5;
			while (!future.isDone() && times > 0) {
				future.awaitUninterruptibly(1000);
				times--;
			}
			if (!future.isDone()) {
				return;
			}
		}
		Server server = null;
		if (servers.containsKey(serverId)) {
			server = servers.get(serverId);
			server.setIp(message.getIp());
			server.setName(message.getName());
			server.setPort(message.getPort());
			server.setServerId(serverId);
			server.setState(ServerState.RUN);
		} else {
			server = new Server();
			server.setIp(message.getIp());
			server.setName(message.getName());
			server.setPort(message.getPort());
			server.setServerId(serverId);
			server.setState(ServerState.RUN);
			servers.put(serverId, server);
		}
		channel.closeFuture().addListener(channelCloseListener);
		channel.attr(AttributeKeys.SERVER_ID).set(serverId);
		channelGroup.add(channel);
		ResRegisterServerMessage.Builder builder = ResRegisterServerMessage.newBuilder();
		builder.setCode(0);
		builder.setServerName(serverName == null ? "server name is null" : serverName);
		channel.writeAndFlush(builder);
	}

	/** 心跳 */
	public void heartbeat(int size, Channel channel) {
		Integer serverId = channel.attr(AttributeKeys.SERVER_ID).get();
		if (serverId == null) {
			return;
		}
		Server server = servers.get(serverId);
		server.setOnline(size);
	}

	/** 获取服务器列表 */
	public void listServer(Channel channel) {
		ResServerListMessage.Builder msg = ResServerListMessage.newBuilder();
		for (Entry<Integer, Server> entry : this.servers.entrySet()) {
			Server server = entry.getValue();
			ServerInfo.Builder builder = ServerInfo.newBuilder();
			builder.setIp(server.getIp());
			builder.setName(server.getName());
			builder.setPort(server.getPort());
			builder.setState(server.getState());
			builder.setServerId(server.getServerId());
			builder.setIsRecommend(server.isRecommend());
			msg.addServerList(builder);
		}
		channel.writeAndFlush(msg);
	}

	/**
	 * 开启httpserver
	 * 
	 * @param port
	 */
	public void startHttpServer(ChannelHandler channelHandler) {
		if (httpPort <= 0) {
			throw new IllegalArgumentException("httpPort is 0");
		}
		httpServerThread = new HttpServerThread("HTTP-SER", httpPort, channelHandler);
		httpServerThread.start();
	}

	/**
	 * 
	 */
	public void stop() {
		if (listeners != null) {
			for (ShutdownListener serverListener : listeners) {
				try {
					serverListener.shutdown();
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}

	public Channel getChannel(int serverId) {
		return channelGroup.getChannel(serverId);
	}

	/**
	 * @return the listeners
	 */
	public List<ShutdownListener> getListeners() {
		return listeners;
	}

	/**
	 * @param listeners
	 *            the listeners to set
	 */
	public void setListeners(List<ShutdownListener> listeners) {
		this.listeners = listeners;
	}

	/**
	 * @return the servers
	 */
	public final ConcurrentMap<Integer, Server> getServers() {
		return servers;
	}

}
