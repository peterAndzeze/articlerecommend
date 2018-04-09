package com.article.recommend.threadpool;


import com.article.recommend.threadpool.manager.AbstractManage;
import com.article.recommend.threadpool.task.Task;

/**
 * 线程池服务
 */
public interface ThreadFactoryService {
	/**
	 * Description:执行交互任务
	 * @author sw
	 * @param workerTask 生产任务
	 * @param consumorTask 消费任务
	 * @return MakeReportManage
	 */
	public AbstractManage execInteractiveTask(Task workerTask, Task consumorTask);
	/**
	 * Description:批量执行单任务
	 * @author sw
	 * @param osTasks 任务数组
	 * @return MakeReportManage
	 */
	public AbstractManage execSingleTasks(Task... osTasks);
}
