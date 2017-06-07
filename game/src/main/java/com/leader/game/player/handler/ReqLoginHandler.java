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
import com.leader.game.protobuf.protocol.SectProtocol.SectInfo;
import com.leader.game.sect.SectManager;
import com.leader.game.sect.model.Sect;

import io.netty.channel.Channel;

@Controller
@Protocol("PlayerProtocol")
public class ReqLoginHandler implements Handler {

	@Override
	public void action(Channel channel, Message m) throws InvalidProtocolBufferException {
		ReqLoginMessage message = (ReqLoginMessage) m;
		String username = message.getUsername();
		ResLoginMessage.Builder response = ResLoginMessage.newBuilder();
		String token = message.getToken();
		Player player = PlayerManager.getInstance().login(username, token, channel, response);
		if (player != null) {
			PlayerInfo.Builder info = response.getPlayerInfoBuilder();
			info.setUid(player.getId());
			info.setNickname(player.getNickname());
			info.setIcon(player.getIcon());
			info.setSex(player.getSex());
			// 门派
			Sect sect = player.getSect();
			SectInfo.Builder sectInfo = SectManager.getInstance().packSect(sect);
			info.setSectInfo(sectInfo);
			response.setPlayerInfo(info);

		}
		channel.writeAndFlush(response);

		if (player != null)
			LogManager.getInstance().addLoginLog(player.getId(), player.getNickname(), player.getUsername(),
					player.getChannel(), 2, 0);
	}
}
