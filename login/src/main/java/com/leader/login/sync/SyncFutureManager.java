package com.leader.login.sync;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.protobuf.Message;
import com.google.protobuf.Message.Builder;
import com.google.protobuf.Parser;
import com.leader.core.server.model.IMessageType;
import com.leader.core.server.model.Protocol;
import com.leader.core.server.pool.LogicThreadPool;
import com.leader.core.util.TextKit;
import com.leader.login.protobuf.protocol.LoginProtocol.ReqSyncMessage;
import com.leader.login.sync.model.SyncHandler;

import io.netty.channel.Channel;

@Service
public class SyncFutureManager {
	@Autowired
	private SyncHandler[] syncHandlers;
	@Autowired
	private IMessageType messageType;
	/** log */
	private Logger log = LoggerFactory.getLogger(getClass());
	/** handlers */
	private HashMap<Short, SyncHandler> handlerMap = new HashMap<>();
	/** 解析器 */
	private HashMap<Short, Parser<? extends Message>> parsers = new HashMap<Short, Parser<? extends Message>>();

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		try {
			Field[] tf = messageType.getClass().getFields();
			HashMap<String, Short> messageMap = new HashMap<>();
			for (Field field : tf) {
				String name = field.getName();
				Short id = field.getShort(messageType);
				messageMap.put(name, id);
			}

			for (SyncHandler syncHandler : syncHandlers) {
				Class<? extends SyncHandler> clazz = syncHandler.getClass();
				String name = TextKit.replace(clazz.getSimpleName(), "Handler", "");
				if (!messageMap.containsKey(name)) {
					log.error("同步响应 {}SyncHandler未能找到对应的MessageType", name);
					return;
				}
				short id = messageMap.get(name);
				handlerMap.put(id, syncHandler);

				Protocol protocol = clazz.getAnnotation(Protocol.class);
				if (protocol != null) {
					String className = "com.leader.login.protobuf.protocol." + protocol.value() + "$" + name
							+ "Message";
					Class<?> c = Class.forName(className);
					Method method = c.getMethod("parser");
					Parser<? extends Message> parser = (Parser<? extends Message>) method.invoke(method);
					parsers.put(id, parser);
				} else {
					throw new NullPointerException("Handler : " + clazz.getName() + " No Annotation!");
				}
			}
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			log.error(e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage(), e);
		} catch (NoSuchMethodException e) {
			log.error(e.getMessage(), e);
		} catch (SecurityException e) {
			log.error(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			log.error(e.getMessage(), e);
		}
	}

	public void action(Channel channel, ReqSyncMessage message) {

		final short descriptor = (short) message.getType();
		LogicThreadPool.threadpool.execute(new Runnable() {
			@Override
			public void run() {
				SyncHandler handler = null;
				try {
					handler = handlerMap.get(descriptor);
					if (handler == null) {
						log.info("Unknown descriptor :" + descriptor);
						return;
					}
					Builder data = handler.action(channel, parsers.get(descriptor).parseFrom(message.getData()));

					ReqSyncMessage.Builder respone = ReqSyncMessage.newBuilder();
					respone.setId(message.getId());
					respone.setType(descriptor);
					respone.setData(data.build().toByteString());
					channel.writeAndFlush(respone);
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		});

	}
}
