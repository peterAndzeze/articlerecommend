package com.article.recommend.threadpool;

import com.article.recommend.threadpool.assist.ManagerContext;
import com.article.recommend.threadpool.manager.AbstractManage;
import com.article.recommend.threadpool.manager.ManagerDataFactory;
import com.article.recommend.threadpool.task.Task;

/**
 * 线程池服务实现
 */
public class ThreadFactoryServiceImpl implements ThreadFactoryService  {
	@Override
	public AbstractManage execInteractiveTask(Task workerTask, Task consumorTask) {
		ManagerDataFactory mdf = ManagerDataFactory.createDataFactory();
		ManagerContext context = mdf.getManagerContext();
		context.setInteractive(true);
		context.setWorkerTask(workerTask);
		context.setConsumorTask(consumorTask);
		AbstractManage manage = mdf.getAbstractManage(context);
		try {
			manage.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return manage;
	}
	@Override
	public  AbstractManage execSingleTasks(Task... osTasks) {
		ManagerDataFactory managerDataFactory = ManagerDataFactory.createDataFactory();
		ManagerContext context = managerDataFactory.getManagerContext();
		context.setTasks(osTasks);
		context.setInteractive(false);
		AbstractManage manage = managerDataFactory.getAbstractManage(context);
		try {
			manage.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return manage;
	}

}
