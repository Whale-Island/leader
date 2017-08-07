package com.leader.game.server.handler;

import org.springframework.stereotype.Controller;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.leader.core.server.handler.Handler;
import com.leader.core.server.model.Protocol;
import com.leader.game.protobuf.protocol.ServerInternalProtocol.ResRegisterServerMessage;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@Protocol("ServerInternalProtocol")
public class ResRegisterServerHandler implements Handler {

	@Override
	public void action(Channel channel, Message m) throws InvalidProtocolBufferException {
		ResRegisterServerMessage message = (ResRegisterServerMessage) m;
		if (message != null && message.getCode() == 0) {
			log.info("OK," + message.getServerName() + " register success.");
		} else {
			log.info("server register complete failure,try again later.message = " + message);
		}
	}

}
