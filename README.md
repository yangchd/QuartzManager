# QuartzManager
定时任务管理工具类

对定时任务相关方法进行整理

主要提供方法：

新增定时任务

void addJob(String jobName,Class<? extends Job> job,String time) throws SchedulerException, ParseException

void addJob(String jobName,String jobGroupName,String triggerName,String triggerGroupName,Class<? extends Job> job,String time)throws SchedulerException, ParseException

修改定时任务时间

void modifyJobTime(String jobName,String time)throws SchedulerException, ParseException

void modifyJobTime(String triggerName,String triggerGroupName,String time)throws SchedulerException, ParseException

删除定时任务

void removeJob(String jobName)throws SchedulerException

void removeJob(String jobName,String jobGroupName,String triggerName,String triggerGroupName)throws SchedulerException

获取定时任务状态

Trigger.TriggerState getJobState(String jobName) throws SchedulerException

Trigger.TriggerState getJobState(String jobName,String triggerGroupName) throws SchedulerException