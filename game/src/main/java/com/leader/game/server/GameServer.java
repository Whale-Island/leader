package com.leader.game.server;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import com.google.protobuf.Message.Builder;
import com.leader.core.server.TcpServer;
import com.leader.core.server.http.HttpServerHandler;
import com.leader.core.server.model.ShutdownListener;
import com.leader.core.server.model.ThreadAdapter;
import com.leader.core.server.pool.LogicThreadPool;
import com.leader.game.mail.handler.MailHandler;
import com.leader.game.server.model.PlayerChannelGroup;
import com.leader.game.server.model.ServerConfig;
import com.leader.game.server.thread.ConnectServerThread;
import com.leader.game.server.thread.HttpServerThread;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GameServer {
	/** channelgroup */
	private PlayerChannelGroup channelGroup = new PlayerChannelGroup(GlobalEventExecutor.INSTANCE);
	/** 关服监听列表 */
	private List<ShutdownListener> listeners;
	@Autowired
	private ThreadAdapter[] threadAdapters;
	@Autowired
	private ServerConfig serverConfig;

	/** http服务线程 */
	private HttpServerThread httpServerThread;
	/** 网关线程 */
	private ConnectServerThread loginServerThread;
	/** 充值服线程 */
	private ConnectServerThread chargeServerThread;

	private static class SigletonHolder {
		static final GameServer INSTANCE = new GameServer();
	}

	public static GameServer getInstance() {
		return SigletonHolder.INSTANCE;
	}

	private ChannelHandler channelHandler = new ChannelInitializer<SocketChannel>() {

		@Override
		public void initChannel(SocketChannel ch) throws Exception {
			ChannelPipeline pipeline = ch.pipeline();
			pipeline.addLast("codec-http", new HttpServerCodec());
			pipeline.addLast("aggregator", new HttpObjectAggregator(1024000));
			HttpServerHandler handler = new HttpServerHandler();
			handler.registerHandler("/mail", new MailHandler());
			pipeline.addLast("handler", handler);
		}
	};

	public void run(AbstractApplicationContext applicationContext) throws IOException, CertificateException {
		// 注册登录服
		loginServerThread = new ConnectServerThread(serverConfig.getGateIp(), serverConfig.getGatePort());
		loginServerThread.getListeners().add(new RegisterServerListener(serverConfig.getServerIp(),
				serverConfig.getServerName(), serverConfig.getServerPort(), serverConfig.getServerId()));
		loginServerThread.start();
		log.info("gatewayServerThread Is Startup.");

		// 注册充值服
		chargeServerThread = new ConnectServerThread(serverConfig.getChargeServerIp(),
				serverConfig.getChargeServerPort());
		chargeServerThread.getListeners().add(new RegisterServerListener(serverConfig.getServerIp(),
				serverConfig.getServerName(), serverConfig.getServerPort(), serverConfig.getServerId()));
		// chargeServerThread.start();
		log.info("chargeServerThread Is Startup.");

		// 启动http服
		httpServerThread = new HttpServerThread("HTTP-SER", serverConfig.getHttpServerPort(), channelHandler);
		httpServerThread.start();

		// 启动tcp
		TcpServer tcpServer = new TcpServer(serverConfig.getServerPort());

		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					ThreadPoolTaskScheduler scheduler = applicationContext.getBean("TaskScheduler",
							ThreadPoolTaskScheduler.class);
					scheduler.shutdown();
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				try {
					tcpServer.stop();
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				try {
					stop();
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				LogicThreadPool.shutdown();
				applicationContext.close();
				log.info("Game Server Is Closed.");
			}
		}));
		tcpServer.start();
	}

	/**
	 * @return the channelGroup
	 */
	public PlayerChannelGroup getChannelGroup() {
		return channelGroup;
	}

	/**
	 * 广播消息
	 * 
	 * @param builder
	 */
	protected void broadcast(Builder builder) {
		channelGroup.broadcast(builder);
	}

	/**
	 * 给网关服发消息
	 * 
	 * @param builder
	 */
	protected void send_gateway_message(Builder builder) {
		loginServerThread.sendMessage(builder);
	}

	/**
	 * 给充值服发消息
	 * 
	 * @param builder
	 */
	protected void send_charge_message(Builder builder) {
		chargeServerThread.sendMessage(builder);
	}

	/** 停服调用方法 */
	private void stop() {
		for (ShutdownListener serverListener : listeners) {
			try {
				serverListener.shutdown();
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		for (int i = 0; i < threadAdapters.length; i++) {
			threadAdapters[i].stop0();
			waitThreadStop(threadAdapters[i]);
		}
	}

	/**
	 * 等待线程停止
	 * 
	 * @param thread
	 */
	private void waitThreadStop(Thread thread) {
		String threadName = thread.getName();
		while (thread.isAlive()) {
			try {
				thread.join(1000);
			} catch (InterruptedException e) {
				log.error(e.getMessage(), e);
			}
			log.info("Waiting for " + threadName + " End.");
		}
		log.info(threadName + " End.");
	}

	public List<ShutdownListener> getListeners() {
		return listeners;
	}

	public void setListeners(List<ShutdownListener> listeners) {
		this.listeners = listeners;
	}

}
