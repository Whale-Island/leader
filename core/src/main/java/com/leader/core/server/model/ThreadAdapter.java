/**
 * 
 */
package com.leader.core.server.model;

/**
 * @author
 *
 */
public class ThreadAdapter extends Thread {

	/**
	 * 
	 */
	public ThreadAdapter() {
	}

	/**
	 * @param target
	 */
	public ThreadAdapter(Runnable target) {
		super(target);
	}

	/**
	 * @param name
	 */
	public ThreadAdapter(String name) {
		super(name);
	}

	/**
	 * @param group
	 * @param target
	 */
	public ThreadAdapter(ThreadGroup group, Runnable target) {
		super(group, target);
	}

	/**
	 * @param group
	 * @param name
	 */
	public ThreadAdapter(ThreadGroup group, String name) {
		super(group, name);
	}

	/**
	 * @param target
	 * @param name
	 */
	public ThreadAdapter(Runnable target, String name) {
		super(target, name);
	}

	/**
	 * @param group
	 * @param target
	 * @param name
	 */
	public ThreadAdapter(ThreadGroup group, Runnable target, String name) {
		super(group, target, name);
	}

	/**
	 * @param group
	 * @param target
	 * @param name
	 * @param stackSize
	 */
	public ThreadAdapter(ThreadGroup group, Runnable target, String name, long stackSize) {
		super(group, target, name, stackSize);
	}

	/**
	 * 线程停止方法
	 */
	public void stop0() {

	}
}
