package com.leader.game.server.model;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.google.protobuf.Message.Builder;

import io.netty.channel.Channel;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.Attribute;
import io.netty.util.concurrent.EventExecutor;

/**
 * The default {@link PlayerChannelGroup} implementation.
 */
public class PlayerChannelGroup extends DefaultChannelGroup {

	private ConcurrentHashMap<Long, Channel> channels = new ConcurrentHashMap<Long, Channel>();

	public PlayerChannelGroup(EventExecutor executor) {
		super(executor);
	}

	@Override
	public boolean remove(Object o) {
		super.remove(o);
		if (!(o instanceof Channel)) {
			return false;
		}
		Channel c = (Channel) o;
		Attribute<Long> attribute = c.attr(AttributeKeys.UID);
		long roleName = attribute.get();
		if (roleName == 0) {
			return false;
		}
		Channel channel = channels.remove(roleName);
		if (channel != null) {
			channel.attr(AttributeKeys.UID).getAndSet(null);
			channel.attr(AttributeKeys.PLAYER).getAndSet(null);
		}
		return true;
	}

	@Override
	public boolean add(Channel channel) {
		boolean add = super.add(channel);
		if (!add) {
			return false;
		}
		Attribute<Long> attribute = channel.attr(AttributeKeys.UID);
		long uid = attribute.get();
		if (uid == 0) {
			return false;
		}
		channels.put(uid, channel);
		return true;
	}

	/** 根据UserId得到 channel */
	public Channel getChannel(long roleId) {
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
		Iterator<Entry<Long, Channel>> iterator = channels.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Long, Channel> entry = iterator.next();
			Channel channel = entry.getValue();
			channel.writeAndFlush(builder);
		}
	}

}
