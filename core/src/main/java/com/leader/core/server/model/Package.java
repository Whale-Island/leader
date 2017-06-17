package com.leader.core.server.model;

public class Package {

	/** descriptor */
	private short descriptor;

	/** protobuf data */
	private byte[] data;

	/**
	 * @return the descriptor
	 */
	public final short getDescriptor() {
		return descriptor;
	}

	/**
	 * @param descriptor
	 *            the descriptor to set
	 */
	public final void setDescriptor(short descriptor) {
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
