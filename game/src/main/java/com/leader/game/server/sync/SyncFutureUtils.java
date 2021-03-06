package com.leader.game.server.sync;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.protobuf.Message.Builder;
import com.leader.core.server.model.DynamicMessageFactory;
import com.leader.game.protobuf.protocol.SyncProtocol.ReqSyncMessage;
import com.leader.game.server.MessageUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum SyncFutureUtils {
	Intstance;
	private ConcurrentMap<Integer, SyncFuture<byte[]>> entrys = new ConcurrentHashMap<Integer, SyncFuture<byte[]>>();
	private AtomicInteger count = new AtomicInteger();

	/** 请求方法 */
	public byte[] request(Builder message) {
		// 获取请求message的描述符
		String name = message.getDescriptorForType().getFullName();
		int type = DynamicMessageFactory.getDescriptor(name);
		// 获取标识符
		if (count.get() >= 2000000000) {
			count.set(0);
		}
		int notifyKey = count.getAndIncrement();

		// 装载同步请求消息
		ReqSyncMessage.Builder builder = ReqSyncMessage.newBuilder();
		SyncFuture<byte[]> future = new SyncFuture<byte[]>();
		builder.setId(notifyKey);
		builder.setType(type);
		builder.setData(message.build().toByteString());
		MessageUtils.send_login_message(builder);
		// TODO 会不会先响应后执行等待呢。。
		return request(future, notifyKey);
	}

	private byte[] request(SyncFuture<byte[]> future, int notifyKey) {
		entrys.put(notifyKey, future);
		try {
			try {
				// 五秒超时
				return future.get(5, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				log.error(e.getMessage(), e);
			} catch (ExecutionException e) {
				log.error(e.getMessage(), e);
			} catch (TimeoutException e) {
				log.error("同步请求消息id{}请求超时！", notifyKey);
				log.error(e.getMessage(), e);
			}
		} finally {
			entrys.remove(notifyKey);
		}
		return null;
	}

	/**
	 * 请求响应
	 * 
	 * @param id
	 */
	public void response(int notifyKey, short type, byte[] data) {
		SyncFuture<byte[]> future = entrys.get(notifyKey);
		if (future == null) {
			log.error("同步响应消息id{}未找到实体！", notifyKey);
			return;
		}
		future.setResponse(data);
		synchronized (future) {
			future.notifyAll();
		}
	}
}
