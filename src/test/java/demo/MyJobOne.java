package demo;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by yangchd on 2017/10/13.
 * 测试任务1
 */
public class MyJobOne implements Job {
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("这里是测试任务1");
    }
}
