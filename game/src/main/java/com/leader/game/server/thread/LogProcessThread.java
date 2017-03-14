/**
 * 
 */
package com.leader.game.server.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.leader.core.server.model.ThreadAdapter;
import com.leader.game.log.LogDaoSupport;
import com.leader.game.log.LogEntity;

/**
 * @author
 *
 */
@Component
public class LogProcessThread extends ThreadAdapter {

	private final Lock lock = new ReentrantLock();
	private final Condition notFull = lock.newCondition();
	private final Condition notEmpty = lock.newCondition();

	private final LogEntity[] items = new LogEntity[10000];
	private int putptr, takeptr;
	private final AtomicInteger count = new AtomicInteger();
	private Logger log = LoggerFactory.getLogger(getClass());
	private volatile boolean stop = false;
	@Autowired
	private LogDaoSupport dao;

	/**
	 * @param name
	 */
	public LogProcessThread() {
		super("Log-Process-Thread");
	}

	/**
	 * 添加entity
	 * 
	 * @param entity
	 */
	public void put(LogEntity entity) {
		lock.lock();
		try {
			while (count.get() == items.length)
				notFull.await();
			items[putptr] = entity;
			if (++putptr == items.length)
				putptr = 0;
			count.getAndIncrement();
			notEmpty.signal();
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Removes at most the given number of available elements from this queue
	 * and adds them to the given collection
	 * 
	 * @param entities
	 * @param maxElements
	 * @throws InterruptedException
	 */
	public void drainTo(List<LogEntity> entities, int maxElements) throws InterruptedException {
		lock.lock();
		try {
			while (count.get() == 0)
				notEmpty.await();
			int length = Math.min(maxElements, count.get());
			for (int i = 0; i < length; i++) {
				entities.add(items[takeptr]);
				items[takeptr] = null;
				if (++takeptr == items.length) {
					takeptr = 0;
				}
			}
			count.getAndAdd(-length);
			notFull.signal();
		} finally {
			lock.unlock();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		List<LogEntity> entities = new ArrayList<LogEntity>();
		while (!stop || count.get() > 0) {
			try {
				drainTo(entities, 200);
				dao.batchInsert(entities);
			} catch (Exception e) {
			} finally {
				entities.clear();
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stop0() {
		stop = true;
	}

}
