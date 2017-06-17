package com.leader.game.server.handler;

import org.springframework.stereotype.Controller;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.leader.core.server.handler.Handler;
import com.leader.core.server.model.Protocol;
import com.leader.game.protobuf.protocol.SyncProtocol.ReqSyncMessage;
import com.leader.game.server.sync.SyncFutureUtils;

import io.netty.channel.Channel;

@Controller
@Protocol("SyncProtocol")
public class ReqSyncHandler implements Handler {

	@Override
	public void action(Channel channel, Message m) throws InvalidProtocolBufferException {
		ReqSyncMessage message = (ReqSyncMessage) m;
		SyncFutureUtils.Intstance.response(message.getId(), (short) message.getType(), message.getData().toByteArray());
	}

}
