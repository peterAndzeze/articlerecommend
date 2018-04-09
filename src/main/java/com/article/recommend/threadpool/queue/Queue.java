package com.article.recommend.threadpool.queue;

import java.util.List;

/**
 * 队列接口
 * @author sw
 *
 */
public interface Queue {
	/**
	 * @author sw
	 * @param o 
	 */
	public void put(Object o);
	/**
	 * @author sw
	 * @return Object
	 * @throws InterruptedException 
	 */
	public Object take() throws InterruptedException;
	/**
	 * @author sw
	 * @param taskList 
	 */
	public void putAll(List<? extends Object> taskList);
	/**
	 * @author sw
	 * @return int 
	 */
	public int size();
}
