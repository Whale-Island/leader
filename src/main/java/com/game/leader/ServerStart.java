package com.game.leader;

import com.game.leader.server.GameServer;

public class ServerStart {
	public static void main(String[] args) {
		GameServer gameServer = new GameServer();
		gameServer.run();
	}
}
