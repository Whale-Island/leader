package com.leader.login.server.handler;

import org.springframework.stereotype.Controller;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.leader.core.server.handler.Handler;
import com.leader.core.server.model.Protocol;
import com.leader.login.protobuf.protocol.LoginProtocol.ReqRegisterServerMessage;
import com.leader.login.server.LoginServer;

import io.netty.channel.Channel;

@Controller
@Protocol("LoginProtocol")
public class ReqRegisterServerHandler implements Handler {

	@Override
	public void action(Channel channel, Message m) throws InvalidProtocolBufferException {
		ReqRegisterServerMessage message = (ReqRegisterServerMessage) m;
		LoginServer.getInstance().register(channel, message);
	}

}
