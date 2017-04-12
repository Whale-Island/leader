package com.leader.game.sect.handler;

import org.springframework.stereotype.Controller;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.leader.core.server.handler.Handler;
import com.leader.core.server.model.Protocol;
import com.leader.game.protobuf.protocol.SectProtocol.ReqCreatSectMessage;
import com.leader.game.protobuf.protocol.SectProtocol.ResCreatSectMessage;
import com.leader.game.protobuf.protocol.SectProtocol.SectInfo;
import com.leader.game.sect.SectManager;
import com.leader.game.sect.model.Sect;

import io.netty.channel.Channel;

@Controller
@Protocol("SectProtocol")
public class ReqCreatSectHandler implements Handler {

	@Override
	public void action(Channel channel, Message m) throws InvalidProtocolBufferException {
		ReqCreatSectMessage message = (ReqCreatSectMessage) m;
		String name = message.getName();

		ResCreatSectMessage.Builder response = ResCreatSectMessage.newBuilder();
		Sect sect = SectManager.Intstance.creatSect(channel, name, response);
		if (sect != null) {
			SectInfo.Builder builder = SectManager.Intstance.packSect(sect);
			response.setInfo(builder);
		}
		channel.writeAndFlush(response);
	}

}
