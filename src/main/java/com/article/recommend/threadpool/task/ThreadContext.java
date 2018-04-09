package com.article.recommend.threadpool.task;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.article.recommend.threadpool.assist.ManagerContext;
import com.article.recommend.threadpool.queue.BlockingQueue;
import com.article.recommend.threadpool.queue.Queue;
import org.apache.log4j.Logger;


/**
 *线程池上下文
 *
 */
public class ThreadContext {
	private Logger logger = Logger.getLogger(getClass());

	/**任务数据*/
	private Queue dataQueue = new BlockingQueue(new LinkedList<Object>());
	/**总数*/
	private int totalCount = -1;
	/**剩余数量**/
	private AtomicInteger limit = new AtomicInteger(-1);
	/***错误数**/
	private AtomicInteger errors = new AtomicInteger(0);
	/**已执行数**/
	private AtomicInteger specials = new AtomicInteger(0);
	/**当前执行任务**/
	private Task currentTask;
	
	private ManagerContext managerContext = null;
	public ThreadContext(Task currentTask) {
		this.currentTask = currentTask;
	}
	/**获取上下文实例*/
	public static ThreadContext getInstance(Task currentTask){
		return new ThreadContext(currentTask);
	}
	/**设置任务数*/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setTaskData(Object data){
		synchronized (this) {
			if(data  instanceof List){
				List list=(List) data;
				dataQueue.putAll(list);
			}else{
				dataQueue.put(data);
			}
			setTotalCount(dataQueue.size());
		}
	}
	 public Queue getDataQueue() {
		 return dataQueue;
	}
	public void setDataQueue(Queue dataQueue) {
		this.dataQueue = dataQueue;
	}
	public int getTotalCount(){
		 return totalCount;
	 }
	 
	 public void markFault(){
		 this.errors.incrementAndGet();
	 }
	 
	 public void markSpecial(){
		 this.specials.incrementAndGet();
	 }
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		this.limit.set(totalCount);
	}
	public int getLimit() {
		return limit.get();
	}
	
	public int getErrors() {
		return errors.get();
	}
	public int getSpecials() {
		return specials.get();
	}
	
	public void setCurrentTask(Task currentTask) {
		this.currentTask = currentTask;
	}
	
	public Task currentTask() {
		return this.currentTask;
	}
	public ManagerContext getManagerContext() {
		return managerContext;
	}
	public void setManagerContext(ManagerContext managerContext) {
		this.managerContext = managerContext;
	}

	public Object take() {
		Object result = null;
		//result = this.dataQueueConverter.take(currentTask().getTaskPattern());
		try {
			logger.info("from data queue take object to hanlder ...["+this.dataQueue.size()+"]");
			result = this.dataQueue.take();
			if(result!=null){
				this.limit.decrementAndGet();
				logger.info("from data queue take object to hanlder ...result:["+result+"]");
			}
		} catch (InterruptedException e) {
		}
		return result;
	}
}
