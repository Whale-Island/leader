package com.game.leader.server.handler;

import com.game.leader.protobuf.protocol.ServerProtocol.ReqHeartbeatMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;

import io.netty.channel.Channel;

public class ReqHeartbeatHandler implements Handler {

	@Override
	public void action(Channel channel, Message message) throws InvalidProtocolBufferException {
		ReqHeartbeatMessage heartbeatMessage = (ReqHeartbeatMessage) message;
	}

}
