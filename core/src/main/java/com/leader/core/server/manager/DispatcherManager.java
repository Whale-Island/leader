package com.leader.core.server.manager;

import org.springframework.beans.factory.annotation.Autowired;

import com.leader.core.server.handler.Handler;
import com.leader.core.server.model.Package;
import com.leader.core.server.pool.LogicThreadPool;
import com.leader.core.server.pool.MessagePool;

import io.netty.channel.ChannelHandlerContext;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DispatcherManager {
	/** handler_pool */
	@Autowired
	private MessagePool messagePool;

	private static class SigletonHolder {
		static final DispatcherManager INSTANCE = new DispatcherManager();
	}

	public static DispatcherManager getInstance() {
		return SigletonHolder.INSTANCE;
	}

	/**
	 * 消息分发
	 * 
	 * @param ctx
	 * @param pk
	 */
	public void dispatcher(ChannelHandlerContext ctx, Package pk) {
		final short descriptor = pk.getDescriptor();
		LogicThreadPool.threadpool.execute(new Runnable() {
			@Override
			public void run() {
				Handler handler = null;
				try {
					handler = messagePool.getHandler(descriptor);
					if (handler == null) {
						log.info("Unknown descriptor :" + descriptor);
						return;
					}
					handler.action(ctx.channel(), messagePool.getParser(descriptor).parseFrom(pk.getData()));
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		});
	}

}
