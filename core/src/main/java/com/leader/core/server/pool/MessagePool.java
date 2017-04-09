package com.leader.core.server.pool;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.protobuf.Message;
import com.google.protobuf.Parser;
import com.leader.core.server.handler.Handler;
import com.leader.core.server.model.DynamicMessageFactory;
import com.leader.core.server.model.MessageType;
import com.leader.core.server.model.Protocol;
import com.leader.core.util.TextKit;

public class MessagePool {

	/** handlers */
	private HashMap<Integer, Handler> handlerMap = new HashMap<Integer, Handler>();
	/** 解析器 */
	private HashMap<Integer, Parser<? extends Message>> parsers = new HashMap<Integer, Parser<? extends Message>>();
	/** log */
	private Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private Handler[] handlers;
	private String packagepath;
	@Autowired
	private MessageType messageType;

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		for (int i = 0; i < handlers.length; i++) {
			Class<?> handlerClass = handlers[i].getClass();
			Protocol protocol = handlerClass.getAnnotation(Protocol.class);
			if (protocol != null) {
				String handlerName = TextKit.replace(handlerClass.getSimpleName(), "Handler", "");
				String className = packagepath + "." + protocol.value() + "$" + handlerName + "Message";
				Class<?> mc = messageType.getClass();
				try {
					Field mf = mc.getField(handlerName);
					int type = (int) mf.get(mc);
					Class<?> c = Class.forName(className);
					Method method = c.getMethod("parser");
					Parser<? extends Message> parser = (Parser<? extends Message>) method.invoke(method);
					register(type, handlers[i], parser, c.getSimpleName());
				} catch (ClassNotFoundException e) {
					log.error(className);
					log.error(e.getMessage(), e);
				} catch (SecurityException e) {
					log.error(e.getMessage(), e);
				} catch (IllegalAccessException e) {
					log.error(e.getMessage(), e);
				} catch (IllegalArgumentException e) {
					log.error(e.getMessage(), e);
				} catch (NoSuchFieldException e) {
					log.error("未能从MessageType中找到对应的Message!");
					log.error(e.getMessage(), e);
				} catch (NoSuchMethodException e) {
					log.error(e.getMessage(), e);
				} catch (InvocationTargetException e) {
					log.error(e.getMessage(), e);
				}
			} else {
				throw new NullPointerException("Handler : " + handlerClass.getName() + " No Annotation!");
			}
		}
	}

	private void register(int type, Handler handlerClass, Parser<? extends Message> parser, String descriptor) {
		handlerMap.put(type, handlerClass);
		parsers.put(type, parser);
		DynamicMessageFactory.put(descriptor, type);
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

	public void setPackagepath(String packagepath) {
		this.packagepath = packagepath;
	}

}
