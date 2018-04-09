package com.article.recommend.threadpool.task;


public class TaskFactory {
	private static TaskFactory taskFactory;
	public static TaskFactory createTaskFactory() {
		if (taskFactory == null) {
			taskFactory = new TaskFactory();
		}
		return taskFactory;
	}
	/**创建任务 线程数 默认10**/
	public Task get(Class<? extends Task> taskClass,int threadNum) throws InstantiationException, IllegalAccessException{
		Task taskInstance = taskClass.newInstance();
		ThreadContext context = ThreadContext.getInstance(taskInstance);
		taskInstance.setThreadContext(context);
		taskInstance.setThreadNum(threadNum);
		return taskInstance;
	}
	
}
