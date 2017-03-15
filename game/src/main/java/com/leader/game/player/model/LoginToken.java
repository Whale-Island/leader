package com.leader.game.player.model;

public class LoginToken {
	private String username;// 玩家帐号
	private String token;// 随机令牌
	private long time;// 令牌生成时间（十五分钟后销毁）

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
}
