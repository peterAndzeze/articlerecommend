package com.article.recommend.threadpool.task;

import com.article.recommend.threadpool.assist.ManagerControlListener;

import java.util.List;


/**
 * 任务
 */
public interface Task {
	/**执行前*/
	public void before(ThreadContext context)  throws InterruptedException;
	/**执行**/
	public void run(ThreadContext context)  throws InterruptedException;
	/***结束**/
	public void end(ThreadContext context)  throws InterruptedException;
	/**任务是否结束**/
	public boolean isEnding();
	
	
	/**异常终止**/
	public void interput();
	/**给任务一个上下文**/
	public void setThreadContext(ThreadContext context);
	/**获取上下文***/
	public ThreadContext getThreadContext();
	/**设置任务执行线程数**/
	public void setThreadNum(Integer num);
	/**获取线程数***/
	public Integer getThreadNum();
	
	public void noticeStopAbstractManager();
	/**
	 * getter 
	 * @return Integer
	 */
	public Integer getCurrentActiveThreaNum();
	
	/**
	 * Description:注册TaskEventListener
	 * @param taskEventLister
	 */
	public void registerTaskEventListener(TaskEventListener taskEventLister);
	/**
	 * Description:获取TaskEvent
	 * @return
	 */
	public List<TaskEventListener> getTaskEvelentListener();
	
	
	/**
	 * setter
	 * @param manageControlListener 
	 */
	public void setManagerControlListener(ManagerControlListener manageControlListener);
	/**
	 * getter 
	 * @return ManagerControlListener
	 */
	public ManagerControlListener getManagerControlListener();
	
}
