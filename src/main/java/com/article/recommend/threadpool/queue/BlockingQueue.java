package com.article.recommend.threadpool.queue;

import java.util.List;
/**
 * Description:同步队列 
 * @author sw
 *
 */
public class BlockingQueue implements Queue {

	private List<Object> c;
	/**
	 * Constructor 
	 * @param c 任务列表
	 */
	public BlockingQueue(List<Object> c) {
		this.c = c;
	}
	/**
	 * @author sw
	 * @param o 
	 */
	public void put(Object o) {
		synchronized (this) {
			c.add(o);
			this.notify();
		}
	}
	/**
	 * @author sw
	 * @return Object
	 * @throws InterruptedException 
	 */
	public synchronized Object take() throws InterruptedException {
		Object o = null;
		synchronized (this) {
			if (c.isEmpty()) {
				this.wait(5 * 1000);
			}			
			if (c.size() > 0) {
				o = c.remove(0);
			}
		}
		return o;
	}
	/**
	 * @author sw
	 * @param taskList 
	 */
	public void putAll(List<? extends Object> taskList) {
		synchronized (this) {
			c.addAll(taskList);
		}
	}
	/**
	 * @author sw
	 * @return int 
	 */
	public int size() {
		synchronized (this) {
			return c.size();
		}
	}
}
