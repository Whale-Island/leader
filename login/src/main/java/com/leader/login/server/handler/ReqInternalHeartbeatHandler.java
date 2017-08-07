package com.leader.login.server.handler;

import org.springframework.stereotype.Controller;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.leader.core.server.handler.Handler;
import com.leader.core.server.model.Protocol;
import com.leader.login.protobuf.protocol.LoginProtocol.ReqInternalHeartbeatMessage;
import com.leader.login.server.LoginServer;

import io.netty.channel.Channel;

@Controller
@Protocol("LoginProtocol")
public class ReqInternalHeartbeatHandler implements Handler {

	@Override
	public void action(Channel channel, Message m) throws InvalidProtocolBufferException {
		ReqInternalHeartbeatMessage message = (ReqInternalHeartbeatMessage) m;
		LoginServer.getInstance().heartbeat(message.getOnline(), channel);
	}

}
