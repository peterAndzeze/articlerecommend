package com.article.recommend.quartz.job;

import com.article.recommend.Util.SpringUtil;
import com.article.recommend.service.importdataservice.ImportDataService;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class TestJob implements BaseJob {
    private static  final Logger logger=Logger.getLogger(TestJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("Test job执行");
        ImportDataService importDataService=  SpringUtil.getBean(ImportDataService.class);
        logger.info(" Test job importDataService:"+importDataService);
        importDataService.testMethod();
    }


}
