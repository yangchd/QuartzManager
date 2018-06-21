package com.yangchd.demo;

import com.yangchd.util.QuartzManager;
import org.quartz.Trigger;

/**
 * @author  yangchd  2017/10/13.
 * 定时任务测试
 */
public class Demo {

    public static void main(String[] args) {
        try {
            System.out.println("【测试demo】开始");

            QuartzManager.addJob("任务1", MyJobOne.class, "0/1 * * * * ?");
            System.out.println("任务1添加成功，时间参数：0/1 * * * * ?");

            QuartzManager.addJob("任务2", MyJobTwo.class, "0/2 * * * * ?");
            System.out.println("任务2添加成功，时间参数：0/2 * * * * ?");

            Trigger.TriggerState state1 = QuartzManager.getJobState("任务1");
            System.out.println("任务1状态" + state1.toString());

            Trigger.TriggerState state2 = QuartzManager.getJobState("任务2");
            System.out.println("任务2状态" + state2.toString());

            Thread.sleep(5000);

            QuartzManager.modifyJobTime("任务2", "0/5 * * * * ?");
            System.out.println("任务2时间参数修改成功，时间参数：0/5 * * * * ?");

            QuartzManager.removeJob("任务1");
            System.out.println("任务1成功停止");
            state1 = QuartzManager.getJobState("任务1");
            System.out.println("任务1状态" + state1.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}