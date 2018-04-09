package com.article.recommend.threadpooltest;

import com.article.recommend.threadpool.ThreadFactoryService;
import com.article.recommend.threadpool.ThreadFactoryServiceImpl;
import com.article.recommend.threadpool.businessTask.TestTask;
import com.article.recommend.threadpool.task.Task;
import com.article.recommend.threadpool.task.ThreadContext;
import org.junit.Test;

public class TaskTest {
    @Test
    public void task() throws Exception {
        ThreadFactoryService threadFactoryService=new ThreadFactoryServiceImpl();
        Task testTask=new TestTask();
        ThreadContext threadContext=new ThreadContext(testTask);

        threadContext.setTaskData("1233");
        testTask.setThreadContext(threadContext);
        testTask.setThreadNum(10);
        threadFactoryService.execSingleTasks(testTask).start();
    }
}
