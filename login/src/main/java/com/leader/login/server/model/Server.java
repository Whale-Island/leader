package com.leader.login.server.model;

import lombok.Getter;
import lombok.Setter;

public class Server {

	private @Getter @Setter int serverId;
	private @Getter @Setter String name;
	private @Getter @Setter String ip;
	private @Getter @Setter int port;
	private @Getter @Setter volatile int state;
	private @Getter @Setter int online;
	private @Getter @Setter volatile boolean isRecommend;

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
