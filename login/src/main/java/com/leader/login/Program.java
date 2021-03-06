package com.leader.login;

import java.io.IOException;
import java.security.cert.CertificateException;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.leader.core.server.http.HttpServerHandler;
import com.leader.login.server.LoginServer;
import com.leader.login.server.handler.ReqSetServerHandler;
import com.leader.login.user.handler.ReqUserLoginHandler;
import com.leader.login.user.handler.ReqUserRegisterHandler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

public class Program {

	public static Logger log = LoggerFactory.getLogger(Program.class);

	private static ChannelHandler channelHandler = new ChannelInitializer<SocketChannel>() {
		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			ChannelPipeline pipeline = ch.pipeline();
			pipeline.addLast("codec-http", new HttpServerCodec());
			pipeline.addLast("aggregator", new HttpObjectAggregator(1024000));
			HttpServerHandler handler = new HttpServerHandler();
			handler.registerHandler("/setServer", new ReqSetServerHandler());
			handler.registerHandler("/loGIN", new ReqUserLoginHandler());
			handler.registerHandler("/rEgIsTER", new ReqUserRegisterHandler());
			pipeline.addLast("handler", handler);
		}
	};

	public static AbstractApplicationContext applicationContext = new ClassPathXmlApplicationContext(
			"/applicationContext.xml");

	public static void main(String[] args) throws CertificateException, SchedulerException, IOException {
		LoginServer server = applicationContext.getBean("server", LoginServer.class);
		server.startHttpServer(channelHandler);
		server.run(applicationContext);
	}
}
