package com.leader.login.server.model;

import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.Channel;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.Attribute;
import io.netty.util.concurrent.EventExecutor;

/**
 * @author
 *
 */
public class ServerChannelGroup extends DefaultChannelGroup {

	private ConcurrentHashMap<Integer, Channel> channels = new ConcurrentHashMap<Integer, Channel>();

	/**
	 * @param executor
	 */
	public ServerChannelGroup(EventExecutor executor) {
		super(executor);
	}

	/**
	 * @param name
	 * @param executor
	 */
	public ServerChannelGroup(String name, EventExecutor executor) {
		super(name, executor);
	}

	@Override
	public boolean remove(Object o) {
		super.remove(o);
		if (!(o instanceof Channel)) {
			return false;
		}
		Channel c = (Channel) o;
		Attribute<Integer> attribute = c.attr(AttributeKeys.SERVER_ID);
		Integer serverId = attribute.get();
		if (serverId == null) {
			return false;
		}
		channels.remove(serverId);
		return true;
	}

	@Override
	public boolean add(Channel channel) {
		boolean add = super.add(channel);
		if (!add) {
			return false;
		}
		Attribute<Integer> attribute = channel.attr(AttributeKeys.SERVER_ID);
		Integer serverId = attribute.get();
		if (serverId == null) {
			return false;
		}
		channels.put(serverId, channel);
		return true;
	}

	/** 根据serverId得到 channel */
	public Channel getChannel(Integer serverId) {
		return channels.get(serverId);
	}

}
