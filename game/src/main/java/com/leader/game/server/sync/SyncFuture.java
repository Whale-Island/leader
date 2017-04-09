package com.leader.game.server.sync;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/***
 * 同步请求实体<br>
 * 具体请求操作需在外部进行
 * 
 * @author siestacat
 *
 * @param <T>
 */
public class SyncFuture<T> implements Future<T> {
	// 因为请求和响应是一一对应的，因此初始化CountDownLatch值为1。
	private CountDownLatch latch = new CountDownLatch(1);
	// 需要响应线程设置的响应结果
	private T response;
	// Futrue的请求时间，用于计算Future是否超时
	private long beginTime = System.currentTimeMillis();

	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		if (latch.getCount() == 0)
			return false;

		latch.countDown();
		return true;
	}

	/** 获取响应结果，直到有结果才返回(不推荐) */
	@Override
	public T get() throws InterruptedException, ExecutionException {
		latch.await();
		return this.response;
	}

	/** 获取响应结果，直到有结果或者超过指定时间就返回 */
	@Override
	public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		if (latch.await(timeout, unit)) {
			return this.response;
		}
		return null;
	}

	// 用于设置响应结果，并且做countDown操作，通知请求线程
	public void setResponse(T response) {
		this.response = response;
		latch.countDown();
	}

	public long getBeginTime() {
		return beginTime;
	}

	@Override
	public boolean isCancelled() {
		if (latch.getCount() == 0)
			return true;
		return false;
	}

	@Override
	public boolean isDone() {
		if (response != null) {
			return true;
		}
		return false;
	}

}
