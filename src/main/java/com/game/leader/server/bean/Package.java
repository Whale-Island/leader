package com.game.leader.server.bean;

public class Package {

	/** descriptor */
	private int descriptor;

	/** protobuf data */
	private byte[] data;

	/**
	 * @return the descriptor
	 */
	public final int getDescriptor() {
		return descriptor;
	}

	/**
	 * @param descriptor
	 *            the descriptor to set
	 */
	public final void setDescriptor(int descriptor) {
		this.descriptor = descriptor;
	}

	/**
	 * @return the data
	 */
	public final byte[] getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public final void setData(byte[] data) {
		this.data = data;
	}

}
