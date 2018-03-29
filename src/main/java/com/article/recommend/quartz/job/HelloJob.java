package com.article.recommend.quartz.job;

import com.article.recommend.Util.SpringUtil;
import com.article.recommend.service.importdataservice.ImportDataService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

public class HelloJob implements BaseJob {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("自定义job执行");
        ImportDataService importDataService=  SpringUtil.getBean(ImportDataService.class);
        System.out.println("importDataService:"+importDataService);
        importDataService.testMethod();
    }


}
