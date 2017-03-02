package com.game.leader;

import java.io.IOException;
import java.security.cert.CertificateException;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.game.leader.server.GameServer;

public class ServerStart {
	public static AbstractApplicationContext applicationContext = new ClassPathXmlApplicationContext(
			"/applicationContext.xml");

	public static void main(String[] args) throws CertificateException, IOException {
		GameServer gameServer = new GameServer();
		gameServer.run(applicationContext);
	}
}
