package com.leader.core.server.handler;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;

import io.netty.channel.Channel;

/**
 * 类说明：消息访问端口，将消息传送接口转换为消息访问接口
 * 
 */
public interface Handler {
	public void action(Channel channel, Message message) throws InvalidProtocolBufferException;
}
