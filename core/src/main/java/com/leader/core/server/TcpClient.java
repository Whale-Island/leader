package com.leader.core.server;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.leader.core.server.code.PackDecoder;
import com.leader.core.server.code.PackEncoder;
import com.leader.core.server.manager.MessageDispatcherHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultThreadFactory;

public class TcpClient {

	private Logger log = LoggerFactory.getLogger(getClass());
	//
	private String ip;
	private int port;
	private EventLoopGroup loopGroup;
	private List<ChannelFutureListener> listeners = new ArrayList<ChannelFutureListener>();

	/**
	 * @param ip
	 * @param port
	 */
	public TcpClient(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	/**
	 * @return the listeners
	 */
	public List<ChannelFutureListener> getListeners() {
		return listeners;
	}

	public class IdleStateEventHandler extends ChannelDuplexHandler {
		@Override
		public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
			if (evt instanceof IdleStateEvent) {
				IdleStateEvent e = (IdleStateEvent) evt;
				if (e.state() == IdleState.READER_IDLE) {
					// log.info(ctx.channel().remoteAddress() +
					// " 30 seconds did not receive message.");
				} else if (e.state() == IdleState.WRITER_IDLE) {
					// ReqHeartbeatMessage.Builder builder =
					// ReqHeartbeatMessage.newBuilder();
					// builder.setOnline(GameServerManager.getInstance().getChannelGroup().size());
					// ctx.channel().writeAndFlush(builder);
				}
			}
		}
	}

	public void start() {
		// Configure the client.
		loopGroup = new NioEventLoopGroup(2, new DefaultThreadFactory("IoConnectorThread"));
		try {
			Bootstrap b = new Bootstrap();
			b.group(loopGroup).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
					.option(ChannelOption.SO_SNDBUF, 32 * 1024).option(ChannelOption.SO_RCVBUF, 32 * 1024)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) throws Exception {
							ChannelPipeline pipeline = ch.pipeline();
							pipeline.addLast(new IdleStateHandler(30, 30, 0));
							pipeline.addLast(new IdleStateEventHandler());
							pipeline.addLast(new LoggingHandler(LogLevel.DEBUG));
							pipeline.addLast(new LengthFieldBasedFrameDecoder(32 * 1024, 0, 2));
							pipeline.addLast(new PackDecoder());
							pipeline.addLast(new PackEncoder());
							pipeline.addLast(new MessageDispatcherHandler());
						}
					});

			// Start the client.
			ChannelFuture f = b.connect(ip, port).sync();
			if (!listeners.isEmpty()) {
				for (ChannelFutureListener channelFutureListener : listeners) {
					f.addListener(channelFutureListener);
				}
			}
			// Wait until the connection is closed.
			f.channel().closeFuture().sync();
			log.info("TcpClient Closed.");
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
		} finally {
			// Shut down the event loop to terminate all threads.
			loopGroup.shutdownGracefully();
		}
	}

	public void stop() {
		loopGroup.shutdownGracefully();
	}
}
