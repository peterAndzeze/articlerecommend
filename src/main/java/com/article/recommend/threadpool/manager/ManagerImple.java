package com.article.recommend.threadpool.manager;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.article.recommend.threadpool.assist.ManagerContext;
import com.article.recommend.threadpool.task.Task;
import com.article.recommend.threadpool.task.TaskEventListener;
import com.article.recommend.threadpool.task.TaskPackage;
import com.article.recommend.threadpool.task.ThreadContext;
import org.apache.log4j.Logger;

/** 任务队列管理 ***/
public class ManagerImple extends AbstractManage {
	private Logger logger = Logger.getLogger(getClass());

	// 生产任务task
	private Task workerTask = null;
	// 消费任务task
	private Task consumorTask = null;
	private Task[] tasks;
	private boolean manageRunningState = false;
	private ExecutorService threadExec;

	public ManagerImple(ManagerContext managerContext) {
		super(managerContext);
		int phenixThreadLimit = initPhenixThreadLimit();
		threadExec = Executors.newFixedThreadPool(phenixThreadLimit);
	}

	/**
	 * 获取系统线程上线
	 * 
	 * @return int
	 */
	private int initPhenixThreadLimit() {
		//从数据库查询
		//int threadLimit = ESBPonst.DEFAULT_CIIS_THREAD_LIMIT;
		int threadLimit=10;
		return threadLimit;
	}

	@Override
	public void interrupt(Task currentTask) {
		currentTask.interput();
	}

	@Override
	public boolean isShutDown() {
		return manageRunningState;
	}

	@Override
	public void shutDown() {
		boolean isShutDown = false;
		if (getManagerContext().isInteractive()) {
			boolean workerEndingFlag = this.getWorkerTask().isEnding();
			boolean consumorEndingFlag = this.getConsumorTask().isEnding();
			isShutDown = workerEndingFlag && consumorEndingFlag;
		} else {
			isShutDown = true;
			for (Task task : getTasks()) {
				if (!task.isEnding()) {
					isShutDown = false;
					break;
				}
			}
		}
		if (isShutDown) {
			manageRunningState = true;
			// getDataQueueConverter().clearup();
//			this.threadExec.shutdown();
			logger.info("线程池销毁...");
		}

	}

	@Override
	public void noticeInterrupt(Task currentTask) {
		int activeThreadNum = currentTask.getCurrentActiveThreaNum();
		if (activeThreadNum == 0) {
			triggerAfterTaskEvent(currentTask);
			shutDown();
			currentTask.setThreadNum(0);
		}
		logger.info(new Date() + " " + currentTask.getClass().getName()+ "未释放线程数量：" + activeThreadNum);

	}

	/**
	 * 触发After任务事件
	 */
	private void triggerAfterTaskEvent(Task currentTask) {
		List<TaskEventListener> taskEventListenerList = null;
		taskEventListenerList = currentTask.getTaskEvelentListener();
		execAfterTaskEvent(taskEventListenerList,currentTask.getThreadContext());
	}

	private void execAfterTaskEvent(List<TaskEventListener> taskEventListenerList, ThreadContext context) {
		logger.info("执行TaskEventListener After Method...begin");
		for (TaskEventListener taskEvenetListener : taskEventListenerList) {
			taskEvenetListener.after(context);
		}
		logger.info("执行TaskEventListener After Method...end");
	}

	@Override
	public void start() throws Exception {
		initTask();
		triggerBeforeTaskEvent();
		if (getManagerContext().isInteractive()) {
			startTask(this.getWorkerTask());
			startTask(this.getConsumorTask());
		} else {
			for (Task task : getTasks()) {
				startTask(task);
			}
		}
	}

	/**
	 * @author sw 启动任务
	 * @param currentTask
	 *            当前任务
	 */
	private void startTask(Task currentTask) {
		int taskNum = currentTask.getThreadNum();
		for (int index = 0; index < taskNum; index++) {
			ThreadContext context = currentTask.getThreadContext();
			TaskPackage taskPack = new TaskPackage(currentTask, context);
			if(!threadExec.isShutdown()){
				threadExec.submit(taskPack);
			}
		}
	}

	/**
	 * 触发Before任务事件
	 */
	private void triggerBeforeTaskEvent() {
		List<TaskEventListener> taskEventListenerList = null;
		if (getManagerContext().isInteractive()) {
			taskEventListenerList = this.getWorkerTask().getTaskEvelentListener();
			execBeforeTaskEvent(taskEventListenerList, this.getWorkerTask().getThreadContext());
			taskEventListenerList = this.getConsumorTask().getTaskEvelentListener();
			execBeforeTaskEvent(taskEventListenerList, this.getConsumorTask().getThreadContext());
		} else {
			for (Task task : getTasks()) {
				taskEventListenerList = task.getTaskEvelentListener();
				execBeforeTaskEvent(taskEventListenerList,
						task.getThreadContext());
			}
		}
	}

	private void execBeforeTaskEvent(List<TaskEventListener> taskEventListenerList, ThreadContext context) {
		logger.info("执行TaskEventListener Before Method...begin");
		for (TaskEventListener taskEvenetListener : taskEventListenerList) {
			taskEvenetListener.before(context);
		}
		logger.info("执行TaskEventListener Before Method...end");
	}

	/**
	 * 初始化任务数据
	 */
	private void initTask() {
		ManagerContext context = getManagerContext();
		if (context.isInteractive()) {
			this.setWorkerTask(managerContext.getWorkerTask());
			this.setConsumorTask(managerContext.getConsumorTask());
			this.getConsumorTask().getThreadContext().setTotalCount(this.getWorkerTask().getThreadContext().getTotalCount());
			doTask(this.getWorkerTask());
			doTask(this.getConsumorTask());
		} else {
			this.setTasks(managerContext.getTasks());
			for (Task task : getTasks()) {
				doTask(task);
			}
		}
	}

	/**
	 * @author sw 设置Task全局属性
	 * @param task
	 */
	private void doTask(Task task) {
		task.setManagerControlListener(this);
		task.getThreadContext().setManagerContext(getManagerContext());
		// task.getThreadContext().setDataQueueConverter(getDataQueueConverter());
		// getDataQueueConverter().register(task.getTaskPattern(),
		// task.getPoolContext().getDataQueue());
	}

	public Task getWorkerTask() {
		return workerTask;
	}

	public void setWorkerTask(Task workerTask) {
		this.workerTask = workerTask;
	}

	public Task getConsumorTask() {
		return consumorTask;
	}

	public void setConsumorTask(Task consumorTask) {
		this.consumorTask = consumorTask;
	}

	public Task[] getTasks() {
		return tasks;
	}

	public void setTasks(Task[] tasks) {
		this.tasks = tasks;
	}

}
