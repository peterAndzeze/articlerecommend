package com.article.recommend.threadpool.assist;

import com.article.recommend.threadpool.manager.AbstractManage;
import com.article.recommend.threadpool.task.Task;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * 管理器上下文
 */
public class ManagerContext {
	private Class<? extends Task> workerTaskClazz;
	private Task workerTask;
	private Class<? extends Task> consumorTaskClazz;
	private Task consumorTask;
	private Map<String,Object> params = Collections.synchronizedMap(new HashMap<String, Object>());
	private Task[] tasks;
	private boolean isInteractive = false;
	@SuppressWarnings("unused")
	private AbstractManage abstractManage = null;

	public Class<? extends Task> getWorkerTaskClazz() {
		return workerTaskClazz;
	}

	public void setWorkerTaskClazz(Class<? extends Task> workerTaskClazz) {
		this.workerTaskClazz = workerTaskClazz;
	}

	public Task getWorkerTask() {
		return workerTask;
	}

	public void setWorkerTask(Task workerTask) {
		this.workerTask = workerTask;
	}

	public Class<? extends Task> getConsumorTaskClazz() {
		return consumorTaskClazz;
	}

	public void setConsumorTaskClazz(Class<? extends Task> consumorTaskClazz) {
		this.consumorTaskClazz = consumorTaskClazz;
	}

	public Task getConsumorTask() {
		return consumorTask;
	}

	public void setConsumorTask(Task consumorTask) {
		this.consumorTask = consumorTask;
	}

	/**
	 * @return the params
	 */
	public Map<String,? extends Object> getParams() {
		return params;
	}

	/**
	 * @param params
	 *            the params to set
	 */
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}


	public void setTasks(Task...tasks){
		this.tasks = tasks;
	}
	
	public Task[] getTasks(){
		return this.tasks;
	}

	public boolean isInteractive() {
		return isInteractive;
	}

	public void setInteractive(boolean isInteractive) {
		this.isInteractive = isInteractive;
	}

	public void setAbstractManage(AbstractManage abstractManage) {
		this.abstractManage = abstractManage;
	}
	
}
