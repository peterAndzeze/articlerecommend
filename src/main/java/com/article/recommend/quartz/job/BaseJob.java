package com.article.recommend.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public interface BaseJob extends Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException ;
}
