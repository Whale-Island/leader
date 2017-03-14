package com.leader.game.server.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ServerConfig {
	/** 服务器编号 */
	@Value("${server_area}")
	private int serverId;
	/** 服务器名字 */
	@Value("${server_name}")
	private String serverName;
	/** 服务器ip地址 */
	@Value("${server_host}")
	private String serverIp;
	/** 服务器tcp端口 */
	@Value("${server_port}")
	private int serverPort;
	/** 网关服ip */
	@Value("${gate_server_ip}")
	private String gateIp;
	/** 网关服port */
	@Value("${gate_server_port}")
	private int gatePort;
	/** http端口 */
	@Value("${server_http_port}")
	private int httpServerPort;
	/** 充值服务器ip地址 */
	@Value("${charge_server_ip}")
	private String chargeServerIp;
	/** 充值服务器port */
	@Value("${charge_server_port}")
	private int chargeServerPort;

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public String getGateIp() {
		return gateIp;
	}

	public void setGateIp(String gateIp) {
		this.gateIp = gateIp;
	}

	public int getGatePort() {
		return gatePort;
	}

	public void setGatePort(int gatePort) {
		this.gatePort = gatePort;
	}

	public int getHttpServerPort() {
		return httpServerPort;
	}

	public void setHttpServerPort(int httpServerPort) {
		this.httpServerPort = httpServerPort;
	}

	public String getChargeServerIp() {
		return chargeServerIp;
	}

	public void setChargeServerIp(String chargeServerIp) {
		this.chargeServerIp = chargeServerIp;
	}

	public int getChargeServerPort() {
		return chargeServerPort;
	}

	public void setChargeServerPort(int chargeServerPort) {
		this.chargeServerPort = chargeServerPort;
	}

}
