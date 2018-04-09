package com.article.recommend.threadpool.task;

public class TaskPackage implements Runnable {
	private Task task;
	private ThreadContext threadContext;
	
	public TaskPackage(Task task, ThreadContext threadContext) {
		super();
		this.task = task;
		this.threadContext = threadContext;
	}

	@Override
	public void run() {
		try {
			while (!this.getTask().isEnding()) {
				task.before(threadContext);
				try {
					task.run(threadContext);
				} catch (RuntimeException e) {
					e.printStackTrace();
					task.end(threadContext);
					continue;
				}
				task.end(threadContext);
			}
		} catch (InterruptedException ignored) {
			
		} finally {
			// 通知线程池线程结束
			task.noticeStopAbstractManager();
		}
	}

	public Task getTask() {
		return task;
	}

	public ThreadContext getThreadContext() {
		return threadContext;
	}

}
