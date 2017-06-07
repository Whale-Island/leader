package com.leader.game.sect.handler;

import org.springframework.stereotype.Controller;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.leader.core.server.handler.Handler;
import com.leader.core.server.model.Protocol;
import com.leader.game.protobuf.protocol.SectProtocol.ResRandomSectNameMessage;
import com.leader.game.sect.SectManager;

import io.netty.channel.Channel;

@Controller
@Protocol("SectProtocol")
public class ReqRandomSectNameHandler implements Handler {

	@Override
	public void action(Channel channel, Message m) throws InvalidProtocolBufferException {
		String name = SectManager.getInstance().randomName();
		ResRandomSectNameMessage.Builder builder = ResRandomSectNameMessage.newBuilder();
		builder.setName(name);

		channel.writeAndFlush(builder);

	}

}
