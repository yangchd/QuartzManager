package com.yangchd.util;

import java.text.ParseException;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.CronScheduleBuilder.cronSchedule;

/**
 * @author  yangchd  2017/10/13.
 * Quartz定时任务管理
 * 使用版本2.2.1
 */
public class QuartzManager {

    private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();
    private static String JOB_GROUP_NAME = "job_group";
    private static String TRIGGER_GROUP_NAME = "trigger_group";

    /**
     * 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
     * @param job  定时任务实现job
     * @param jobName   任务名称
     * @param time      任务时间策略
     */
    public static void addJob(String jobName,Class<? extends Job> job,String time)
            throws SchedulerException, ParseException{
        //设置任务
        JobDetail jobDetail = JobBuilder.newJob(job).withIdentity(jobName,JOB_GROUP_NAME).build();
        //设置触发器
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(jobName,TRIGGER_GROUP_NAME)
                .withSchedule(cronSchedule(time)).build();

        //获取Scheduler，并启动任务
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.scheduleJob(jobDetail,trigger);
        //启动
        if(!scheduler.isShutdown()){
            scheduler.start();
        }
    }

    /**
     * 添加一个定时任务
     * @param jobName 任务名
     * @param jobGroupName 任务组名
     * @param triggerName  触发器名
     * @param triggerGroupName 触发器组名
     * @param job     任务
     * @param time    时间设置，参考quartz说明文档
     */
    public static void addJob(String jobName,String jobGroupName,
                              String triggerName,String triggerGroupName,
                              Class<? extends Job> job,String time)
            throws SchedulerException, ParseException{
        //设置任务
        JobDetail jobDetail = JobBuilder.newJob(job).withIdentity(jobName,jobGroupName).build();
        //设置触发器
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerName,triggerGroupName)
                .withSchedule(cronSchedule(time)).build();
        //获取Scheduler，并启动任务
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.scheduleJob(jobDetail,trigger);
        //启动
        if(!scheduler.isShutdown()){
            scheduler.start();
        }
    }

    /**
     * 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
     * @param jobName   任务名
     * @param time      时间参数
     */
    public static void modifyJobTime(String jobName,String time)
            throws SchedulerException, ParseException{
        Scheduler scheduler = schedulerFactory.getScheduler();
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName,TRIGGER_GROUP_NAME);
        Trigger trigger = scheduler.getTrigger(triggerKey);
        if(null == trigger){
            return;
        }
        CronTrigger cronTrigger = (CronTrigger) trigger;
        String oldTime = cronTrigger.getCronExpression();
        if(!oldTime.equals(time)){
            // 触发器
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            // 触发器名,触发器组
            triggerBuilder.withIdentity(jobName, TRIGGER_GROUP_NAME);
            // 触发器时间设定
            triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(time));
            // 创建Trigger对象
            trigger = triggerBuilder.build();
            // 方式一 ：修改一个任务的触发时间
            scheduler.rescheduleJob(triggerKey, trigger);
        }
    }

    /**
     * 修改一个任务的触发时间
     * @param triggerName           触发器名称
     * @param triggerGroupName      触发器组
     * @param time                  时间参数
     */
    public static void modifyJobTime(String triggerName,String triggerGroupName,
                                     String time)
            throws SchedulerException, ParseException{

        Scheduler scheduler = schedulerFactory.getScheduler();
        TriggerKey triggerKey = TriggerKey.triggerKey(triggerName,triggerGroupName);
        Trigger trigger = scheduler.getTrigger(triggerKey);
        if(null == trigger){
            return;
        }
        CronTrigger cronTrigger = (CronTrigger) trigger;
        String oldTime = cronTrigger.getCronExpression();
        if(!oldTime.equals(time)){
            // 触发器
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            // 触发器名,触发器组
            triggerBuilder.withIdentity(triggerName, triggerGroupName);
            // 触发器时间设定
            triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(time));
            // 创建Trigger对象
            trigger = triggerBuilder.build();
            // 方式一 ：修改一个任务的触发时间
            scheduler.rescheduleJob(triggerKey, trigger);
        }
    }

    /**
     * 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
     * @param jobName 任务名称
     */
    public static void removeJob(String jobName)
            throws SchedulerException{
        Scheduler scheduler = schedulerFactory.getScheduler();
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName,TRIGGER_GROUP_NAME);
        JobKey jobKey = JobKey.jobKey(jobName,JOB_GROUP_NAME);
        //停止触发器
        scheduler.pauseTrigger(triggerKey);
        //移除触发器
        scheduler.unscheduleJob(triggerKey);
        //删除任务
        scheduler.deleteJob(jobKey);
    }

    /**
     * 移除一个任务
     * @param jobName           任务名称
     * @param jobGroupName      任务组
     * @param triggerName       触发器名称
     * @param triggerGroupName  触发器组
     */
    public static void removeJob(String jobName,String jobGroupName,
                                 String triggerName,String triggerGroupName)
            throws SchedulerException{
        Scheduler scheduler = schedulerFactory.getScheduler();
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName,jobGroupName);
        JobKey jobKey = JobKey.jobKey(triggerName,triggerGroupName);
        //停止触发器
        scheduler.pauseTrigger(triggerKey);
        //移除触发器
        scheduler.unscheduleJob(triggerKey);
        //删除任务
        scheduler.deleteJob(jobKey);
    }

    /**
     * 获取定时任务运行状态
     * @param jobName       任务名称
     */
    public static Trigger.TriggerState getJobState(String jobName) throws SchedulerException {
        Scheduler scheduler = schedulerFactory.getScheduler();
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName,TRIGGER_GROUP_NAME);
        return scheduler.getTriggerState(triggerKey);
    }
    public static Trigger.TriggerState getJobState(String jobName,String triggerGroupName) throws SchedulerException {
        Scheduler scheduler = schedulerFactory.getScheduler();
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName,triggerGroupName);
        return scheduler.getTriggerState(triggerKey);
    }
}