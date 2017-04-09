package com.leader.game.player.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class LoginToken {
	private @Setter @Getter String username;// 玩家帐号
	private @Setter @Getter String token;// 随机令牌
	private @Setter @Getter long time;// 离线时间（十五分钟后销毁）
}
