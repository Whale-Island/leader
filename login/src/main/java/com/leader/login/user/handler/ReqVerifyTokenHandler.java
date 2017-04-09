package com.leader.login.user.handler;

import org.springframework.stereotype.Controller;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.leader.core.server.handler.Handler;
import com.leader.core.server.model.Protocol;
import com.leader.login.protobuf.protocol.LoginProtocol.ReqVerifyTokenMessage;
import com.leader.login.protobuf.protocol.LoginProtocol.ResVerifyTokenMessage;
import com.leader.login.user.UserManager;

import io.netty.channel.Channel;

@Controller
@Protocol("GatewayProtocol")
public class ReqVerifyTokenHandler implements Handler {

	@Override
	public void action(Channel channel, Message m) throws InvalidProtocolBufferException {
		ReqVerifyTokenMessage message = (ReqVerifyTokenMessage) m;

		int result = UserManager.getInstance().verifyToken(message.getUsername(), message.getToken());

		ResVerifyTokenMessage.Builder builder = ResVerifyTokenMessage.newBuilder();
		builder.setCode(result);
		channel.writeAndFlush(builder);
	}

}
