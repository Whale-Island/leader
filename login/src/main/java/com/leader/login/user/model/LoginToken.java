package com.leader.login.user.model;

import lombok.Getter;
import lombok.Setter;

public class LoginToken {
	private @Getter @Setter String username;// 玩家帐号
	private @Getter @Setter String token;// 随机令牌
	private @Getter long time = System.currentTimeMillis();// 令牌生成时间（十五分钟后销毁）

}
