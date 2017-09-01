package com.leader.login.sync.model;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.Message.Builder;

import io.netty.channel.Channel;

public interface SyncHandler {
	public Builder action(Channel channel, Message m) throws InvalidProtocolBufferException;

}
