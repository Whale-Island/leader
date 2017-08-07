/**
 * 
 */
package com.game.leader.server.bean;

/**
 * @author
 *
 */
public class ThreadAdapter extends Thread {

	/**
	 * 
	 */
	public ThreadAdapter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param target
	 */
	public ThreadAdapter(Runnable target) {
		super(target);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param name
	 */
	public ThreadAdapter(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param group
	 * @param target
	 */
	public ThreadAdapter(ThreadGroup group, Runnable target) {
		super(group, target);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param group
	 * @param name
	 */
	public ThreadAdapter(ThreadGroup group, String name) {
		super(group, name);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param target
	 * @param name
	 */
	public ThreadAdapter(Runnable target, String name) {
		super(target, name);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param group
	 * @param target
	 * @param name
	 */
	public ThreadAdapter(ThreadGroup group, Runnable target, String name) {
		super(group, target, name);
		// TODO Auto-generated constructor stub
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
