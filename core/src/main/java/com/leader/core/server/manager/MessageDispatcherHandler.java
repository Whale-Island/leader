package com.leader.core.server.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.leader.core.server.model.Package;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * MessageDispatcher
 */
public class MessageDispatcherHandler extends SimpleChannelInboundHandler<Package> {

	/** log */
	private static Logger log = LoggerFactory.getLogger(MessageDispatcherHandler.class);

	@Override
	public void channelActive(final ChannelHandlerContext ctx) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.netty.channel.SimpleChannelInboundHandler#channelRead0(io.netty.
	 * channel .ChannelHandlerContext, java.lang.Object)
	 */
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Package pk) throws Exception {
		DispatcherManager.getInstance().dispatcher(ctx, pk);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.error(cause.getMessage(), cause);
	}

}
