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
import com.leader.core.server.model.IMessageType;
import com.leader.core.server.model.Protocol;
import com.leader.core.util.TextKit;

public class MessagePool {

	/** handlers */
	private HashMap<Short, Handler> handlerMap = new HashMap<Short, Handler>();
	/** 解析器 */
	private HashMap<Short, Parser<? extends Message>> parsers = new HashMap<Short, Parser<? extends Message>>();
	/** log */
	private Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private Handler[] handlers;
	private String packagepath;
	@Autowired
	private IMessageType messageType;

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		HashMap<String, Handler> handlerMap = new HashMap<>();
		for (int i = 0; i < handlers.length; i++) {
			String handlerName = TextKit.replace(handlers[i].getClass().getSimpleName(), "Handler", "");
			handlerMap.put(handlerName, handlers[i]);
		}

		Field[] tf = messageType.getClass().getFields();
		for (Field field : tf) {
			String name = field.getName();
			Class<?> mc = messageType.getClass();
			try {
				Field mf = mc.getField(name);
				Handler handler = handlerMap.get(name);
				short type = (short) mf.get(mc);
				if (handler != null) {
					Class<?> handlerClass = handler.getClass();
					Protocol protocol = handlerClass.getAnnotation(Protocol.class);
					if (protocol != null) {
						String className = packagepath + "." + protocol.value() + "$" + name + "Message";
						Class<?> c = Class.forName(className);
						Method method = c.getMethod("parser");
						Parser<? extends Message> parser = (Parser<? extends Message>) method.invoke(method);
						register(type, handler, parser);
					} else {
						throw new NullPointerException("Handler : " + handlerClass.getName() + " No Annotation!");
					}
				}
				registerDynamicMessage(type, name);
			} catch (ClassNotFoundException e) {
				log.error(name);
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
		}
	}

	private void registerDynamicMessage(short type, String descriptor) {
		DynamicMessageFactory.put(descriptor, type);
	}

	private void register(short type, Handler handlerClass, Parser<? extends Message> parser) {
		handlerMap.put(type, handlerClass);
		parsers.put(type, parser);

	}

	/**
	 * 获取handler
	 * 
	 * @param descriptor
	 * @return
	 */
	public Handler getHandler(short descriptor) {
		return handlerMap.get(descriptor);
	}

	/**
	 * 获取parser
	 * 
	 * @param descriptor
	 * @return
	 */
	public Parser<? extends Message> getParser(short descriptor) {
		return parsers.get(descriptor);
	}

	public void setPackagepath(String packagepath) {
		this.packagepath = packagepath;
	}

}
