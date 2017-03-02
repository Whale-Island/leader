package com.game.leader.server.pool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadPool {
	private static Logger log = LoggerFactory.getLogger(ThreadPool.class);

	/** 游戏逻辑处理线程池 */
	public static final ScheduledExecutorService threadpool = Executors.newScheduledThreadPool(4);

	public static void shutdown() {
		try {
			threadpool.shutdown();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
