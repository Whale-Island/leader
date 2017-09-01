package com.leader.login.user.handler;

import org.springframework.stereotype.Controller;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.Message.Builder;
import com.leader.core.server.model.Protocol;
import com.leader.login.protobuf.protocol.LoginProtocol.ReqVerifyTokenMessage;
import com.leader.login.protobuf.protocol.LoginProtocol.ResVerifyTokenMessage;
import com.leader.login.sync.model.SyncHandler;
import com.leader.login.user.UserManager;

import io.netty.channel.Channel;

@Controller
@Protocol("LoginProtocol")
public class ReqVerifyTokenHandler implements SyncHandler {

	@Override
	public Builder action(Channel channel, Message m) throws InvalidProtocolBufferException {
		ReqVerifyTokenMessage message = (ReqVerifyTokenMessage) m;

		int result = UserManager.getInstance().verifyToken(message.getUsername(), message.getToken());

		ResVerifyTokenMessage.Builder builder = ResVerifyTokenMessage.newBuilder();
		builder.setCode(result);
		return builder;
	}

}
