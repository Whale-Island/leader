package com.leader.game.server;

import com.google.protobuf.Message.Builder;
import com.leader.game.player.model.Player;

import io.netty.channel.Channel;

public class MessageUtils {
	/**
	 * 通知玩家消息
	 *
	 * @param me
	 *            玩家
	 * @param message
	 *            消息
	 */
	public static void send_player_message(Player player, Builder message) {
		Channel channel = GameServer.Intstance.getChannelGroup().getChannel(player.getId());
		if (channel == null || !channel.isActive()) {
			return;
		}
		channel.writeAndFlush(message);
	}

	/**
	 * 给全服发消息
	 * 
	 * @param message
	 */
	public static void send_all_message(Builder message) {
		GameServer.Intstance.broadcast(message);
	}

	/***
	 * 发消息给网关
	 * 
	 * @param message
	 */
	public static void send_gateway_message(Builder message) {
		GameServer.Intstance.send_gateway_message(message);
	}

	/**
	 * 发消息给充值服
	 * 
	 * @param builder
	 */
	public void send_charge_message(Builder message) {
		GameServer.Intstance.send_charge_message(message);
	}
}
