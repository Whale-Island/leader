package com.leader.login.server;

import java.net.InetSocketAddress;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.net.ssl.SSLException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import com.leader.core.server.HttpServerThread;
import com.leader.core.server.TcpServer;
import com.leader.core.server.model.ShutdownListener;
import com.leader.core.server.pool.LogicThreadPool;
import com.leader.core.util.TextKit;
import com.leader.login.protobuf.protocol.LoginProtocol.ReqRegisterServerMessage;
import com.leader.login.protobuf.protocol.LoginProtocol.ResRegisterServerMessage;
import com.leader.login.server.model.AttributeKeys;
import com.leader.login.server.model.Server;
import com.leader.login.server.model.ServerChannelGroup;
import com.leader.login.server.model.ServerState;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.util.Attribute;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginServer {
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

	private LoginServer() {
	}

	private static class SigletonHolder {
		static final LoginServer INSTANCE = new LoginServer();
	}

	public static LoginServer getInstance() {
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
			log.info("remote host " + ip + " is closed.");
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
					log.error(e.getMessage(), e);
				}
				try {
					if (httpServerThread != null) {
						httpServerThread.stop0();
					}
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				try {
					tcpServer.stop();
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				stop();
				try {
					applicationContext.close();
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				log.info(serverName + " stoped.");
			}
		}));
		log.info(serverName + " started.");
		tcpServer.start();
	}

	/** 游戏服务器注册 */
	public void register(Channel channel, ReqRegisterServerMessage message) {
		int serverID = message.getServerID();
		Channel oldChannel = channelGroup.getChannel(serverID);
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
		if (servers.containsKey(serverID)) {
			server = servers.get(serverID);
			server.setIp(message.getIp());
			server.setName(message.getName());
			server.setPort(message.getPort());
			server.setServerId(serverID);
			server.setState(ServerState.RUN);
		} else {
			server = new Server();
			server.setIp(message.getIp());
			server.setName(message.getName());
			server.setPort(message.getPort());
			server.setServerId(serverID);
			server.setState(ServerState.RUN);
			servers.put(serverID, server);
		}
		channel.closeFuture().addListener(channelCloseListener);
		channel.attr(AttributeKeys.SERVER_ID).set(serverID);
		channelGroup.add(channel);
		ResRegisterServerMessage.Builder builder = ResRegisterServerMessage.newBuilder();
		builder.setCode(0);
		builder.setServerName(serverName == null ? "server name is null" : serverName);
		channel.writeAndFlush(builder);

		log.info("服务器{}注册成功！", serverID);
	}

	/** 心跳 */
	public void heartbeat(int online, Channel channel) {
		Integer serverId = channel.attr(AttributeKeys.SERVER_ID).get();
		if (serverId == null) {
			return;
		}
		Server server = servers.get(serverId);
		server.setOnline(online);
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
					log.error(e.getMessage(), e);
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
