package demo;

import com.yangchd.util.QuartzManager;
import org.quartz.Trigger;

/**
 * Created by yangchd on 2017/10/13.
 * 定时任务测试
 */
public class Demo {

    public static void main(String[] args) {
        try {
            System.out.println("【系统启动】开始(每1秒输出一次)...");

            QuartzManager.addJob("任务1",MyJobOne.class,"0/1 * * * * ?");

            QuartzManager.addJob("任务2",MyJobTwo.class,"0/2 * * * * ?");

            Trigger.TriggerState state = QuartzManager.getJobState("任务1");
            System.out.println("任务1状态"+state.toString());

            Thread.sleep(5000);
            QuartzManager.modifyJobTime("任务2","0/5 * * * * ?");
            QuartzManager.removeJob("任务1");
            state = QuartzManager.getJobState("任务1");
            System.out.println("任务1状态"+state.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}