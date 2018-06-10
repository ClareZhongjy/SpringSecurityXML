package com.TechPlat.test;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.TechPlat.model.ScheduledJob;

public class dsTest {

     private static Scheduler scheduler;

    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-quartz.xml");

         scheduler = (StdScheduler)ac.getBean("scheduler");

         startSchedule();
    }

    public static void startSchedule() {
        try {
        	
        	
        	
            // 1、创建一个JobDetail实例，指定Quartz
            JobDetail jobDetail = JobBuilder.newJob(dsJob.class)
            // 任务执行类
                    .withIdentity("job1_2", "jGroup1")
                    // 任务名，任务组
                    .build();

            // 触发器类型
            //SimpleScheduleBuilder builder = SimpleScheduleBuilder
                    // 设置执行次数
                    //.repeatSecondlyForTotalCount(5);

            CronScheduleBuilder builder = CronScheduleBuilder.cronSchedule("0/5 * * * * ?");
            // 2、创建Trigger
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger1_2", "tGroup1").startNow()
                    .withSchedule(builder)
                    .build();

            // 3、创建Scheduler
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            // 4、调度执行
            scheduler.scheduleJob(jobDetail, trigger);

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
    
    public void addJob(){
    	ScheduledJob job = new ScheduledJob();
        job.setJobId("10001");
        job.setJobName("data_import");
        job.setJobGroup("dataWork");
        job.setJobStatus("1");
        job.setCronExpression("0/5 * * * * ?");
        job.setDesc("数据导入任务");
        
    }

}