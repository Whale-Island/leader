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
import com.leader.game.protobuf.protocol.PlayerProtocol.ReqRegisterMessage;
import com.leader.game.protobuf.protocol.PlayerProtocol.ResRegisterMessage;

import io.netty.channel.Channel;

@Controller
@Protocol("PlayerProtocol")
public class ReqRegisterHandler implements Handler {

	@Override
	public void action(Channel channel, Message m) throws InvalidProtocolBufferException {
		ReqRegisterMessage message = (ReqRegisterMessage) m;
		String nickname = message.getNickname();
		String icon = message.getIcon();
		int sex = message.getSex();
		int gameChannel = message.getChannel();
		String deviceId = message.getDeviceId();

		ResRegisterMessage.Builder response = ResRegisterMessage.newBuilder();

		Player player = PlayerManager.Intstance.register(channel, nickname, icon, sex, gameChannel, deviceId, response);

		if (player != null) {
			PlayerInfo.Builder info = response.getPlayerInfoBuilder();
			info.setUid(player.getId());
			info.setNickname(player.getNickname());
			info.setIcon(player.getIcon());
			info.setSex(player.getSex());

			response.setPlayerInfo(info);
		}
		channel.writeAndFlush(response);

		if (player != null)
			LogManager.Intstance.addRegisterLog(player.getId(), player.getDeviceId(), player.getNickname(),
					player.getNickname(), player.getChannel());
	}

}
