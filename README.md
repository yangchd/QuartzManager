# QuartzManager
定时任务管理工具类

对定时任务相关方法进行整理

主要提供方法：

新增定时任务

addJob(String jobName,Class<? extends Job> job,String time) 

addJob(String jobName,String jobGroupName,String triggerName,String triggerGroupName,Class<? extends Job> job,String time)

修改定时任务时间

modifyJobTime(String jobName,String time)

modifyJobTime(String triggerName,String triggerGroupName,String time)

删除定时任务

void removeJob(String jobName)

void removeJob(String jobName,String jobGroupName,String triggerName,String triggerGroupName)

获取定时任务状态

Trigger.TriggerState getJobState(String jobName)

Trigger.TriggerState getJobState(String jobName,String triggerGroupName)