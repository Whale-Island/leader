package com.leader.game.player.handler;

import org.springframework.stereotype.Controller;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.leader.core.server.handler.Handler;
import com.leader.core.server.model.Protocol;
import com.leader.game.log.manager.LogManager;
import com.leader.game.player.PlayerManager;
import com.leader.game.player.model.Player;
import com.leader.game.protobuf.protocol.PlayerProtocol.PlayerInfo;
import com.leader.game.protobuf.protocol.PlayerProtocol.ReqLoginMessage;
import com.leader.game.protobuf.protocol.PlayerProtocol.ResLoginMessage;

import io.netty.channel.Channel;

@Controller
@Protocol("PlayerProtocol")
public class ReqLoginHandler implements Handler {

	@Override
	public void action(Channel channel, Message m) throws InvalidProtocolBufferException {
		ReqLoginMessage message = (ReqLoginMessage) m;
		String username = message.getUsername();
		String token = message.getToken();
		ResLoginMessage.Builder respone = ResLoginMessage.newBuilder();
		Player player = PlayerManager.Intstance.login(username, token, channel, respone);
		if (player != null) {
			PlayerInfo.Builder info = respone.getPlayerInfoBuilder();
			info.setUid(player.getId());
			info.setNickname(player.getNickname());

			respone.setPlayerInfo(info);
		}
		channel.writeAndFlush(respone);

		if (player != null)
			LogManager.Intstance.addLoginLog(player.getId(), player.getNickname(), player.getUsername(),
					player.getChannel(), 2, 0);
	}
}
