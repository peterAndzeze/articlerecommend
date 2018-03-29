package com.article.recommend.service.quartzservice;

import com.article.recommend.constant.RecommendConstant;
import com.article.recommend.entity.QuartzInfo;
import com.article.recommend.mapper.localMapper.QuartzMapper;
import com.article.recommend.quartz.QuartzManager;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 定时任务
 */
@Service
public class QuartzService {
    @Autowired
    private QuartzMapper quartzMapper;
    @Autowired
    private QuartzManager quartzManager;
    public List<QuartzInfo> getQuartzInfos(){
        return quartzMapper.getQuartzInfos();
    }

    public QuartzInfo getQuartzInfoById(Long id){
        return quartzMapper.getQuartzInfoById(id);
    }

    /**
     * 更新
     * @param quartzInfo
     */
    @Transactional(value = "localDataTransactionManager",propagation = Propagation.REQUIRED)
    public void updateQuartzInfo(QuartzInfo quartzInfo) throws SchedulerException {
        quartzMapper.updateQuartz(quartzInfo);
        quartzManager.reschedule(quartzInfo.getClassName(),quartzInfo.getGroup(),quartzInfo.getCron());
    }

    /**
     * 暂停
     * @param quartzInfo
     */
    @Transactional(value = "localDataTransactionManager",propagation = Propagation.REQUIRED)
    public  void  paused(QuartzInfo quartzInfo) throws SchedulerException {
        quartzInfo.setIsRun(RecommendConstant.SYSTEM_DATE_INVALID);
        quartzMapper.updateQuartz(quartzInfo);
        quartzManager.paused(quartzInfo.getClassName(),quartzInfo.getGroup());
    }


    /**
     * 恢复
     * @param quartzInfo
     */
    @Transactional(value = "localDataTransactionManager",propagation = Propagation.REQUIRED)
    public  void  resume(QuartzInfo quartzInfo) throws SchedulerException {
        quartzInfo.setIsRun(RecommendConstant.SYSTEM_DATE_EFFECTIVE);
        quartzMapper.updateQuartz(quartzInfo);
        quartzManager.resume(quartzInfo.getClassName(),quartzInfo.getGroup());
    }

    /**
     * 新增
     * @param quartzInfo
     */
    @Transactional(value = "localDataTransactionManager",propagation = Propagation.REQUIRED)
    public void addQuartzInfo(QuartzInfo quartzInfo){
        quartzInfo.setState(RecommendConstant.SYSTEM_DATE_EFFECTIVE);
        quartzMapper.inserQuartz(quartzInfo);
        quartzManager.addJob(quartzInfo.getClassName(),quartzInfo.getGroup(),quartzInfo.getCron(),quartzInfo.getIsRun());
    }

    /**
     * 删除
     * @param id
     */
    @Transactional(value = "localDataTransactionManager",propagation = Propagation.REQUIRED)
    public void deleteQuartzInfo(Long id) throws SchedulerException {
        QuartzInfo quartzInfo=getQuartzInfoById(id);
        quartzMapper.deleteQuartz(id);
        quartzManager.delete(quartzInfo.getClassName(),quartzInfo.getGroup());
    }
}
