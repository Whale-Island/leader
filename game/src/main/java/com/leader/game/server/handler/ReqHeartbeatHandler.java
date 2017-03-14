package com.leader.game.server.handler;

import org.springframework.stereotype.Controller;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.leader.core.server.handler.Handler;
import com.leader.core.server.model.Protocol;

import io.netty.channel.Channel;

@Controller
@Protocol("ServerProtocol")
public class ReqHeartbeatHandler implements Handler {

	@Override
	public void action(Channel channel, Message message) throws InvalidProtocolBufferException {

	}

}
