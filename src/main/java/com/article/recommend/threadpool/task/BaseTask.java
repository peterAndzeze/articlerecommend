package com.article.recommend.threadpool.task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import com.article.recommend.threadpool.assist.ManagerControlListener;
import org.apache.log4j.Logger;

public abstract class BaseTask implements Task {
	/**
	 * 任务线程数
	 */
	private Integer threadNum;
	protected Logger logger = Logger.getLogger(getClass());
	protected AtomicInteger activeThreadNum = new AtomicInteger(0);
	protected AtomicBoolean endingFlag = new AtomicBoolean(false);
	protected ThreadContext context;
	protected ManagerControlListener manageControlListener;
	protected List<TaskEventListener> taskEventListenerList = new ArrayList<TaskEventListener>();
	
	
	/**
	 * Description:同步对象
	 * @author 
	 * @return Object
	 */
	public abstract Object getSyncObject();

	/**
	 * 初始化
	 */
	public abstract void init();
	/**
	 * Constructor
	 */
	public BaseTask() {
		init();
	}
	
	/**
	 * before run method
	 * @author 
	 * @param context 任务执行上下文
	 * @throws InterruptedException 
	 */
	public void before(ThreadContext context) throws InterruptedException {
	}
	

	/**
	 * @author 
	 * @param context 任务执行上下文
	 * @throws InterruptedException 
	 */
	public void end(ThreadContext context) throws InterruptedException {
//		int size = context.currentTaskDataSize();
//		boolean isComplete = context.getDataQueueConverter().isComplete();
//		logger.info("任务是否结束-->size:"+size+";isComplete:"+isComplete);
//		if (size == 0&&isComplete) {
		int totalCount = context.getTotalCount();
		int limit = context.getLimit();
		int errors = context.getErrors();
		int special = context.getSpecials();
		int result = (limit - errors-special);
		logger.info("任务总数："+ totalCount + ",剩余任务数："+limit+",失败任务数："+errors+",特殊标记数："+special+",判断条件："+result);
		if(result == 0){
			ManagerControlListener controlListener = this.getManagerControlListener();
			controlListener.interrupt(this);
		}
	}

	public void registerTaskEventListener(TaskEventListener taskEventLister) {
		taskEventListenerList.add(taskEventLister);
	}	
	
	public List<TaskEventListener> getTaskEvelentListener() {
		return taskEventListenerList;
	}	
	/**
	 * @return the threadNum
	 */
	public Integer getThreadNum() {
		return threadNum;
	}

	/**
	 * @param threadNum
	 *            the threadNum to set
	 */
	public void setThreadNum(Integer threadNum) {
		this.threadNum = threadNum;
		this.activeThreadNum.set(threadNum);
	}



	@Override
	public Integer getCurrentActiveThreaNum() {
		return this.activeThreadNum.get();
	}

	@Override
	public synchronized void noticeStopAbstractManager() {
		this.activeThreadNum.decrementAndGet();
		this.getManagerControlListener().noticeInterrupt(this);
	}

	@Override
	public void interput() {
		this.endingFlag.set(true);
	}

	@Override
	public boolean isEnding() {
		return this.endingFlag.get();
	}

	@Override
	public void setManagerControlListener(ManagerControlListener manageControlListener) {
		this.manageControlListener = manageControlListener;
	}

	@Override
	public ManagerControlListener getManagerControlListener() {
		return manageControlListener;
	}


	@Override
	public void setThreadContext(ThreadContext context) {
		this.context = context;
	}

	@Override
	public ThreadContext getThreadContext() {
		return context;
	}
	
//	@Override
//	public abstract TaskPattern getTaskPattern() ;
	
}
