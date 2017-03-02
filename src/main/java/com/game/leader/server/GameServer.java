package com.game.leader.server;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import com.game.leader.server.bean.ShutdownListener;
import com.game.leader.server.bean.ThreadAdapter;
import com.game.leader.server.pool.ThreadPool;

public class GameServer {
	/** 日志 */
	private Logger log = LoggerFactory.getLogger(GameServer.class);
	/** 关服监听列表 */
	private List<ShutdownListener> listeners;
	private ThreadAdapter[] threadAdapters;

	public void run(AbstractApplicationContext applicationContext) throws IOException, CertificateException {
		TcpServer tcpServer = new TcpServer(0);
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
				ThreadPool.shutdown();
				applicationContext.close();
				log.info("Game Server Is Closed.");
			}
		}));
		tcpServer.start();
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

}
