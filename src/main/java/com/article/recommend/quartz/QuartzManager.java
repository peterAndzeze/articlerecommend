package com.article.recommend.quartz;

import com.article.recommend.constant.RecommendConstant;
import com.article.recommend.quartz.job.BaseJob;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * 定时任务管理器
 *
 *
 * None：Trigger已经完成，且不会在执行，或者找不到该触发器，或者Trigger已经被删除
 NORMAL:正常状态
 PAUSED：暂停状态
 COMPLETE：触发器完成，但是任务可能还正在执行中
 BLOCKED：线程阻塞状态
 ERROR：出现错误
 *
 */
@Service
public class QuartzManager {
    private static  final Logger log=Logger.getLogger(QuartzManager.class);
    @Autowired@Qualifier("scheduler")
    private Scheduler scheduler;
    /**
     * 暂停任务
     * @param jobClassName 任务类
     * @param group 所在组
     */
    public void paused(String jobClassName,String group) throws SchedulerException {
        try {
           log.info("暂停任务:"+jobClassName+","+group+"start");
            scheduler.pauseJob(JobKey.jobKey(jobClassName,group));
            log.info("暂停任务:"+jobClassName+","+group+"end");
        } catch (SchedulerException e) {
            log.error("暂停任务失败:"+jobClassName+","+group+":"+e.getMessage());
            e.printStackTrace();
            throw  new SchedulerException("暂停失败");
        }

    }

    /**
     * 恢复定时任务
     * @param jobClassName 任务类
     * @param group 所在组
     */
    public void resume(String jobClassName,String group) throws SchedulerException {
        try {
            log.info("恢复任务:"+jobClassName+","+group+"start");
            scheduler.resumeJob(JobKey.jobKey(jobClassName,group));
            log.info("恢复任务:"+jobClassName+","+group+"end");
        } catch (SchedulerException e) {
            log.error("恢复任务失败:"+jobClassName+","+group+":"+e.getMessage());
            e.printStackTrace();
            throw  new SchedulerException("恢复失败");

        }
    }

    /**
     * 重新刷新job cron
     * @param jobClassName 任务类
     * @param group 组
     * @param cron 新的cron表达式
     */
    public void  reschedule(String jobClassName,String group,String cron) throws SchedulerException {
        try {
            log.info("更新cron:"+jobClassName+","+group+","+cron+" start");
            TriggerKey triggerKey = TriggerKey.triggerKey(jobClassName, group);
            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);

            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
            log.info("更新cron:"+jobClassName+","+group+","+cron+" start");

        } catch (SchedulerException e) {
            log.error("更新cron:"+jobClassName+","+group+","+cron+" 失败："+e.getMessage());
            throw  new SchedulerException("更新失败");
        }
    }

    /**
     * 删除任务
     * @param jobClassName 任务类
     * @param group 组
     */
    public void delete(String jobClassName,String group) throws SchedulerException {
        try{
            log.info("删除任务:"+jobClassName+","+group+"start");
            scheduler.pauseTrigger(TriggerKey.triggerKey(jobClassName, group));
            scheduler.unscheduleJob(TriggerKey.triggerKey(jobClassName, group));
            scheduler.deleteJob(JobKey.jobKey(jobClassName, group));
            log.info("删除任务:"+jobClassName+","+group+"end");
        }catch (SchedulerException e){
            log.error("删除任务:"+jobClassName+","+group+"失败:"+e.getMessage());
            e.printStackTrace();
            throw  new SchedulerException("删除失败");
        }
    }

    /**
     * 新增
     * @param className
     * @param group
     * @param cron
     * @param isRun
     */
    public void addJob(String className,String group,String cron,String isRun){
        try {
           log.info("scheduler 是否启动:"+scheduler.isStarted());
           boolean isExists=scheduler.checkExists(new JobKey(className,group));
           if(isExists){
               log.info("已经存在***");
               return ;
           }
            //启动调度器
            if( !scheduler.isStarted()){
                scheduler.start();
            }
            //构建job信息
            JobDetail jobDetail = JobBuilder.newJob(getClass(className).getClass()).withIdentity(className, group).build();
            //表达式调度构建器(即任务执行的时间)
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
            //按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(className, group)
                    .withSchedule(scheduleBuilder).build();
            scheduler.scheduleJob(jobDetail, trigger);
            if(isRun.equals(RecommendConstant.SYSTEM_DATE_INVALID)){//如果定时任务是暂停状态
                paused(className,group);//停止
            }
        } catch (SchedulerException e1) {
            log.error("初始化有问题***"+e1.getMessage());
            e1.printStackTrace();
        }catch (Exception e){
            log.error("job初始化失败:"+e.getMessage());
            e.printStackTrace();
        }
    }
    /**
     * 获取类
     * @param classname
     * @return
     * @throws Exception
     */
    protected static BaseJob getClass(String classname) throws Exception {
        Class<?> class1 = Class.forName(classname);
        return (BaseJob)class1.newInstance();
    }

   /* public String getJobState(String className,String group){
        JobDetail jobDetail=scheduler.getJobDetail(new JobKey(className,group));
        jobDetail.
    }*/

}
