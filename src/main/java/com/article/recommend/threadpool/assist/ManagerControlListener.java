package com.article.recommend.threadpool.assist;


import com.article.recommend.threadpool.task.Task;

/**
 * 
 * 外层服务控制监听
 */
public interface ManagerControlListener {
	/**
	 * Description:终止任务
	 * @author sw
	 * @param currentTask 当前执行任务
	 */
	public void interrupt(Task currentTask);
	
	/**
	 * getter 
	 * @return MakeReportContext
	 */
	public ManagerContext getManagerContext();
	/**
	 * Description:是否关闭线程池
	 * @author sw
	 * @return boolean
	 */
	public boolean isShutDown();
	/**
	 * Description:关闭线程池
	 * @author sw
	 */
	public void shutDown();
	/**
	 * Description:通知ReportManage终止任务
	 * @author sw
	 * @param currentTask 当前任务
	 */
	public void noticeInterrupt(Task currentTask);
}
