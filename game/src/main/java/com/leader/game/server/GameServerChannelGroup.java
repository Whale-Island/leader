package com.leader.game.server;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.google.protobuf.Message.Builder;
import com.leader.game.server.model.AttributeKeys;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.Attribute;
import io.netty.util.concurrent.EventExecutor;

/**
 * The default {@link ChannelGroup} implementation.
 */
public class GameServerChannelGroup extends DefaultChannelGroup {

	private ConcurrentHashMap<Integer, Channel> channels = new ConcurrentHashMap<Integer, Channel>();

	public GameServerChannelGroup(EventExecutor executor) {
		super(executor);
	}

	@Override
	public boolean remove(Object o) {
		super.remove(o);
		if (!(o instanceof Channel)) {
			return false;
		}
		Channel c = (Channel) o;
		Attribute<Integer> attribute = c.attr(AttributeKeys.ROLE_ID);
		int roleName = attribute.get();
		if (roleName == 0) {
			return false;
		}
		Channel channel = channels.remove(roleName);
		if (channel != null) {
			channel.attr(AttributeKeys.ROLE_ID).getAndSet(null);
			channel.attr(AttributeKeys.ROLE).getAndSet(null);
		}
		return true;
	}

	@Override
	public boolean add(Channel channel) {
		boolean add = super.add(channel);
		if (!add) {
			return false;
		}
		Attribute<Integer> attribute = channel.attr(AttributeKeys.ROLE_ID);
		int roleId = attribute.get();
		if (roleId == 0) {
			return false;
		}
		channels.put(roleId, channel);
		return true;
	}

	/** 根据UserId得到 channel */
	public Channel getChannel(int roleId) {
		return channels.get(roleId);
	}

	@Override
	public int size() {
		return channels.size();
	}

	/**
	 * 广播消息
	 * 
	 * @param builder
	 */
	public void broadcast(Builder builder) {
		Iterator<Entry<Integer, Channel>> iterator = channels.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Integer, Channel> entry = iterator.next();
			Channel channel = entry.getValue();
			channel.writeAndFlush(builder);
		}
	}

}
