/**
 * 
 */
package com.leader.core.server.model;

/**
 * 关服监听器
 * 
 * @author
 *
 */
public interface ShutdownListener {
	/** 关服调用方法 */
	public void shutdown();
}
