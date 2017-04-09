package com.leader.game.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.leader.game.player.PlayerManager;

@Component
public class ClearLoginTokenJob {

	@Scheduled(cron = "0 */1 * * * ?")
	public void execute() {
		PlayerManager.Intstance.clearToken();
	}

}
