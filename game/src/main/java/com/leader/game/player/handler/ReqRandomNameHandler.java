package com.leader.game.player.handler;

import org.springframework.stereotype.Controller;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.leader.core.server.handler.Handler;
import com.leader.core.server.model.Protocol;
import com.leader.game.player.PlayerManager;
import com.leader.game.protobuf.protocol.PlayerProtocol.ResRandomNameMessage;

import io.netty.channel.Channel;

@Controller
@Protocol("PlayerProtocol")
public class ReqRandomNameHandler implements Handler {

	@Override
	public void action(Channel channel, Message m) throws InvalidProtocolBufferException {
		String name = PlayerManager.getInstance().randomName();
		ResRandomNameMessage.Builder builder = ResRandomNameMessage.newBuilder();
		builder.setName(name);

		channel.writeAndFlush(builder);
	}
}
