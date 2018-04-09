package com.article.recommend.threadpool.businessTask;

import com.article.recommend.threadpool.task.BaseTask;
import com.article.recommend.threadpool.task.ThreadContext;

import java.util.Map;

public class TestTask extends BaseTask {
    @Override
    public Object getSyncObject() {
        return null;
    }

    @Override
    public void init() {

    }

    @Override
    public void run(ThreadContext context) throws InterruptedException {
        System.out.println("获取"+context.take());
    }
}
