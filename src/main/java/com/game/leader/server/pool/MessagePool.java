package com.game.leader.server.pool;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.game.leader.server.bean.Protocol;
import com.game.leader.server.handler.Handler;
import com.game.leader.util.TextKit;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Message;
import com.google.protobuf.Parser;

public class MessagePool {

	/** handlers */
	private HashMap<Integer, Handler> handlerMap = new HashMap<Integer, Handler>();
	/** 解析器 */
	private HashMap<Integer, Parser<? extends Message>> parsers = new HashMap<Integer, Parser<? extends Message>>();
	/** log */
	private Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private Handler[] handlers;

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		for (int i = 0; i < handlers.length; i++) {
			Class<?> handlerClass = handlers[i].getClass();
			Protocol protocol = handlerClass.getAnnotation(Protocol.class);
			if (protocol != null) {
				String handlerName = handlerClass.getSimpleName();
				String className = "com.game.leader.protobuf.protocol." + protocol.value() + "$"
						+ TextKit.replace(handlerName, "Handler", "Message");
				try {
					Class<?> c = Class.forName(className);
					Method method = c.getMethod("getDescriptor");
					Descriptor descriptor = (Descriptor) method.invoke(method);
					String descriptor0 = descriptor.getFullName();
					Field field = c.getField("PARSER");
					Parser<? extends Message> parser = (Parser<? extends Message>) field.get(c);
					register(0, handlers[i], parser);
				} catch (ClassNotFoundException e) {
					log.error(e.getMessage(), e);
				} catch (NoSuchMethodException e) {
					log.error(e.getMessage(), e);
				} catch (SecurityException e) {
					log.error(e.getMessage(), e);
				} catch (IllegalAccessException e) {
					log.error(e.getMessage(), e);
				} catch (IllegalArgumentException e) {
					log.error(e.getMessage(), e);
				} catch (InvocationTargetException e) {
					log.error(e.getMessage(), e);
				} catch (NoSuchFieldException e) {
					log.error(e.getMessage(), e);
				}
			} else {
				throw new NullPointerException("Handler : " + handlerClass.getName() + " No Annotation!");
			}
		}
	}

	private void register(int descriptor, Handler handlerClass, Parser<? extends Message> parser) {
		handlerMap.put(descriptor, handlerClass);
		parsers.put(descriptor, parser);
	}

	/**
	 * 获取handler
	 * 
	 * @param descriptor
	 * @return
	 */
	public Handler getHandler(int descriptor) {
		return handlerMap.get(descriptor);
	}

	/**
	 * 获取parser
	 * 
	 * @param descriptor
	 * @return
	 */
	public Parser<? extends Message> getParser(int descriptor) {
		return parsers.get(descriptor);
	}

}
