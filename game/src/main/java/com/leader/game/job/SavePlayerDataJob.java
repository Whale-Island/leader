package com.leader.game.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.leader.game.player.PlayerManager;

@Component
public class SavePlayerDataJob {

	@Scheduled(cron = "0 0/15 * * * ?")
	public void execute() {
		PlayerManager.getInstance().save();
	}

}
