package com.leader.game;

import java.io.IOException;
import java.security.cert.CertificateException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.leader.game.server.GameServer;

public class Program {

	public static Logger logger = LoggerFactory.getLogger(Program.class);

	public static AbstractApplicationContext applicationContext = new ClassPathXmlApplicationContext(
			"/applicationContext.xml");

	public static void main(String[] args) {
		try {
			GameServer.getInstance().run(applicationContext);
		} catch (CertificateException | IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
}
