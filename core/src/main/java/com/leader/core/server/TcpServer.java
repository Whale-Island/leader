package com.leader.core.server;

import java.security.cert.CertificateException;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.net.ssl.SSLException;

import com.leader.core.server.code.PackDecoder;
import com.leader.core.server.code.PackEncoder;
import com.leader.core.server.manager.MessageDispatcherHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * TcpServer, received data from a client.
 * 
 * @author
 */
@Slf4j
public final class TcpServer {

	private static final boolean SSL = System.getProperty("ssl") != null;
	private AtomicBoolean state = new AtomicBoolean(); // true is run
	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;
	private int port;

	public TcpServer(int port) {
		this.port = port;
	}

	public class IdleStateEventHandler extends ChannelDuplexHandler {
		@Override
		public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
			if (evt instanceof IdleStateEvent) {
				IdleStateEvent e = (IdleStateEvent) evt;
				if (e.state() == IdleState.READER_IDLE) {
					Channel channel = ctx.channel();
					log.info(channel.remoteAddress() + " within 60 seconds did not receive message, kicked");
					channel.close();
				}
			}
		}
	}

	public void start() throws SSLException, CertificateException {
		if (!state.compareAndSet(false, true)) {
			throw new IllegalStateException("Failed to start Socket.IO server: server already started");
		}
		// Configure SSL.
		final SslContext sslCtx;
		if (SSL) {
			SelfSignedCertificate ssc = new SelfSignedCertificate();
			sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
		} else {
			sslCtx = null;
		}

		// Configure the server.
		bossGroup = new NioEventLoopGroup(1, new DefaultThreadFactory("IoAcceptorThread"));
		workerGroup = new NioEventLoopGroup(2, new DefaultThreadFactory("IoProcessorThread"));
		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)// \n
					.option(ChannelOption.SO_BACKLOG, 100)// 连接请求队列长度
					.option(ChannelOption.TCP_NODELAY, true)// 禁用Nagle算法,使用于小数据即时传输
					.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)// ByteBuf采用内存池分配\n
					.option(ChannelOption.SO_SNDBUF, 32 * 1024)// 缓冲区大小32K \n
					.option(ChannelOption.SO_RCVBUF, 32 * 1024)// 接收缓冲区
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) throws Exception {
							ChannelPipeline pipeline = ch.pipeline();
							if (sslCtx != null) {
								pipeline.addLast(sslCtx.newHandler(ch.alloc()));
							}
							pipeline.addLast(new LoggingHandler(LogLevel.TRACE));
							pipeline.addLast(new IdleStateHandler(60, 60, 100)); // 关闭空闲连接
							pipeline.addLast(new IdleStateEventHandler());
							pipeline.addLast(new LengthFieldBasedFrameDecoder(32 * 1024, 0, 2));
							pipeline.addLast(new PackDecoder());
							pipeline.addLast(new PackEncoder());
							pipeline.addLast(new MessageDispatcherHandler());
						}
					});
			// Start the server.
			ChannelFuture f = bootstrap.bind(port).sync();
			log.info("Socket server started at port " + port + '.');
			// Wait until the server socket is closed.
			f.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			log.error(e.getLocalizedMessage(), e);
		} finally {
			// Shut down all event loops to terminate all threads.
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	/**
	 * Stops Socket.IO server.
	 * 
	 * @throws IllegalStateException
	 *             if server already stopped
	 */
	public void stop() {
		if (!state.compareAndSet(true, false)) {
			throw new IllegalStateException("Failed to stop Socket.IO server: server already stopped");
		}
		log.info("Socket.IO server stopping");

		// timer.stop();
		// Shut down all event loops to terminate all threads.
		bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();

		log.info("Socket.IO server stopped");
	}

	/**
	 * Restarts Socket.IO server. If server already started it stops server;
	 * otherwise it just starts server.
	 */
	public void restart() {
		if (isStarted()) {
			stop();
		}
		try {
			start();
		} catch (SSLException | CertificateException e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * Returns if server is in started state or not.
	 */
	public boolean isStarted() {
		return state.compareAndSet(true, true);
	}

	/**
	 * Returns if server is in stopped state or not.
	 */
	public boolean isStopped() {
		return state.compareAndSet(false, false);
	}

}
