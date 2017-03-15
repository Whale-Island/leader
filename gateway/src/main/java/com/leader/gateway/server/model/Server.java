package com.leader.gateway.server.model;

public class Server {

	private int serverId;
	private String name;
	private String ip;
	private int port;
	private volatile int state;
	private int online;
	private volatile boolean isRecommend;

	/**
	 * @return the serverId
	 */
	public final int getServerId() {
		return serverId;
	}

	/**
	 * @param serverId
	 *            the serverId to set
	 */
	public final void setServerId(int serverId) {
		this.serverId = serverId;
	}

	/**
	 * @return the name
	 */
	public final String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public final void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the ip
	 */
	public final String getIp() {
		return ip;
	}

	/**
	 * @param ip
	 *            the ip to set
	 */
	public final void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @return the port
	 */
	public final int getPort() {
		return port;
	}

	/**
	 * @param port
	 *            the port to set
	 */
	public final void setPort(int port) {
		this.port = port;
	}

	/**
	 * @return the state
	 */
	public final int getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public final void setState(int state) {
		this.state = state;
	}

	/**
	 * @return the online
	 */
	public final int getOnline() {
		return online;
	}

	/**
	 * @param online
	 *            the online to set
	 */
	public final void setOnline(int online) {
		this.online = online;
	}

	/**
	 * @return the isRecommend
	 */
	public final boolean isRecommend() {
		return isRecommend;
	}

	/**
	 * @param isRecommend
	 *            the isRecommend to set
	 */
	public final void setRecommend(boolean isRecommend) {
		this.isRecommend = isRecommend;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + serverId;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Server other = (Server) obj;
		if (serverId != other.serverId)
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Server [serverId=").append(serverId).append(", name=").append(name).append(", ip=").append(ip)
				.append(", port=").append(port).append(", state=").append(state).append("]");
		return builder.toString();
	}

}
