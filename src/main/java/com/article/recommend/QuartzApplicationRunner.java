package com.article.recommend;

import com.article.recommend.Util.SpringUtil;
import com.article.recommend.constant.RecommendConstant;
import com.article.recommend.entity.QuartzInfo;
import com.article.recommend.quartz.QuartzManager;
import com.article.recommend.quartz.job.BaseJob;
import com.article.recommend.service.quartzservice.QuartzService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Size;
import java.util.List;

@Component
@Order(1)//定义了多个后
public class QuartzApplicationRunner implements ApplicationRunner {
    @Autowired
    private QuartzService quartzService;
    @Autowired
    private QuartzManager quartzManager;
    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        System.out.println("项目启动后加载定时任务start****");
        List<QuartzInfo> quartzInfos=quartzService.getQuartzInfos();
        quartzInfos.forEach(quartzInfo->{
            if(quartzInfo.getState().equals(RecommendConstant.SYSTEM_DATE_EFFECTIVE) ){//失效数据
                quartzManager.addJob(quartzInfo.getClassName(),quartzInfo.getGroup(),quartzInfo.getCron(),quartzInfo.getIsRun());
            }
        });
        System.out.println("项目启动后加载定时任务end****");
    }


}
