package com.article.recommend.threadpool.task;


/**
 * 任务监听
 */
public interface TaskEventListener {
	public void before(ThreadContext context);
	
	public void after(ThreadContext context);
}
