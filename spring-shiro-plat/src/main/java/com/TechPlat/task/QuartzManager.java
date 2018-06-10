package com.TechPlat.task;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.TechPlat.model.ScheduledJob;
import com.alibaba.druid.util.StringUtils;  
import static org.quartz.CronScheduleBuilder.*; 
  
/** 
 *  
 * @Description 
 * @author zjy 
 */  
public class QuartzManager  {  
	
//	ApplicationContext ac = new ClassPathXmlApplicationContext("spring-quartz.xml");
//	Scheduler scheduler = (StdScheduler)ac.getBean("scheduler");
    
    private static SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();    
    private static String JOB_GROUP_NAME = "Test_Group";    
    private static String TRIGGER_GROUP_NAME = "Test_TRIGGERGROUP_NAME";    
    
  
    /**
     *  @Description: 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名 
     * @param cls 任务处理类
     * @param scheduleJob 任务详细信息
     * @return
     */
    @SuppressWarnings("unchecked")    
    public static boolean addJob(Class cls,ScheduledJob scheduleJob) {    
        try {    
            Scheduler sched = gSchedulerFactory.getScheduler();    

            String jobName = scheduleJob.getJobName();
            String cronExpress = scheduleJob.getCronExpression();
            
            JobKey jobKey = new JobKey(jobName,JOB_GROUP_NAME);
            JobDetail jobDetail = JobBuilder.newJob(cls).withIdentity(jobKey).build();
            
            TriggerKey triggerKey = new TriggerKey(jobName,TRIGGER_GROUP_NAME);
            CronScheduleBuilder schedBuilder = CronScheduleBuilder.cronSchedule(cronExpress);
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey)
            		.withSchedule(schedBuilder).build();
            
            sched.scheduleJob(jobDetail, trigger);
            
            if (!sched.isShutdown()) {  
                sched.start();// 启动  
            }  
            return true;
        } catch (Exception e) {    
        	e.printStackTrace();
            return false;
        }    
    }    
    /** 
     * @Description: 添加一个定时任务  
     * @param jobName 任务名 
     * @param jobGroupName 任务组名 
     * @param triggerName 触发器名  
     * @param triggerGroupName 触发器组名 
     * @param jobClass 任务 
     * @param time 时间设置，参考quartz说明文档 
     */  
    @SuppressWarnings("unchecked")    
    public static void addJob(String jobName, String jobGroupName,    
            String triggerName, String triggerGroupName, Class jobClass,    
            String time) {    
        try {    
            Scheduler sched = gSchedulerFactory.getScheduler();    
            JobDetail job = JobBuilder.newJob(jobClass)  
                    .withIdentity(jobName, jobGroupName)  
                    .build();  
            // 表达式调度构建器  
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(time);  
            // 按新的cronExpression表达式构建一个新的trigger  
            Trigger trigger = TriggerBuilder  
                    .newTrigger()  
                    .withIdentity(triggerName, triggerGroupName)  
                            .withSchedule(scheduleBuilder).build();  
            sched.scheduleJob(job, trigger);   
            // 启动    
            if (!sched.isShutdown()) {    
                sched.start();    
            }    
        } catch (Exception e) {    
            throw new RuntimeException(e);    
        }    
    }    
    
    /** 
     * @Description: 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名)  
     * @param jobName 
     * @param time 
     */  
    @SuppressWarnings("unchecked")    
    public static void modifyJobTime(String jobName, String time) {   
        TriggerKey triggerKey = TriggerKey.triggerKey(  
                jobName, TRIGGER_GROUP_NAME);  
          
        try {    
            Scheduler sched = gSchedulerFactory.getScheduler();    
            CronTrigger trigger =(CronTrigger) sched.getTrigger(triggerKey);  
            if (trigger == null) {    
                return;    
            }    
            String oldTime = trigger.getCronExpression();    
            if (!oldTime.equalsIgnoreCase(time)) {  
                CronScheduleBuilder scheduleBuilder =CronScheduleBuilder.cronSchedule(time);  
                //按新的cronExpression表达式重新构建trigger  
                trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)  
                .withSchedule(scheduleBuilder).build();  
                //按新的trigger重新设置job执行  
                sched.rescheduleJob(triggerKey, trigger);  
            }    
        } catch (Exception e) {    
            throw new RuntimeException(e);    
        }    
    }    
    
    /** 
     * @Description: 修改一个任务的触发时间  
     * @param triggerName 
     * @param triggerGroupName 
     * @param time 
     */  
    public static void modifyJobTime(String triggerName,    
            String triggerGroupName, String time) {   
        TriggerKey triggerKey = TriggerKey.triggerKey(  
                triggerName, triggerGroupName);  
        try {    
            Scheduler sched = gSchedulerFactory.getScheduler();    
            CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey);    
            if (trigger == null) {    
                return;    
            }    
            String oldTime = trigger.getCronExpression();    
            if (!oldTime.equalsIgnoreCase(time)) {    
                // trigger已存在，则更新相应的定时设置  
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder  
              .cronSchedule(time);  
                // 按新的cronExpression表达式重新构建trigger  
                trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)  
                        .withSchedule(scheduleBuilder).build();  
                // 按新的trigger重新设置job执行  
                sched.resumeTrigger(triggerKey);  
            }    
        } catch (Exception e) {    
            throw new RuntimeException(e);    
        }    
    }    
    
    /** 
     * @Description 移除一个任务(使用默认的任务组名，触发器名，触发器组名) 
     * @param jobName 
     */  
    public static void removeJob(String jobName) {   
        TriggerKey triggerKey = TriggerKey.triggerKey(  
                jobName, TRIGGER_GROUP_NAME);  
        JobKey jobKey = JobKey.jobKey(jobName, JOB_GROUP_NAME);  
        try {    
            Scheduler sched = gSchedulerFactory.getScheduler();  
            Trigger trigger = (Trigger) sched.getTrigger(triggerKey);    
            if (trigger == null) {    
                return;    
            }  
            sched.pauseTrigger(triggerKey);;// 停止触发器    
            sched.unscheduleJob(triggerKey);// 移除触发器    
            sched.deleteJob(jobKey);// 删除任务    
        } catch (Exception e) {    
            throw new RuntimeException(e);    
        }    
    }    
    
    /**  
     * @Description: 移除一个任务  
     * @param jobName  
     * @param jobGroupName  
     * @param triggerName  
     * @param triggerGroupName  
     */    
    public static void removeJob(String jobName, String jobGroupName,    
            String triggerName, String triggerGroupName) {   
        TriggerKey triggerKey = TriggerKey.triggerKey(  
                jobName, triggerGroupName);  
        JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);  
        try {    
            Scheduler sched = gSchedulerFactory.getScheduler();   
            sched.pauseTrigger(triggerKey);// 停止触发器    
            sched.unscheduleJob(triggerKey);// 移除触发器    
            sched.deleteJob(jobKey);// 删除任务  
        } catch (Exception e) {    
            throw new RuntimeException(e);    
        }    
    }   
    /** 
     * @Description:暂停一个任务 
     * @param jobName 
     * @param jobGroupName 
     */  
    public static void pauseJob(String jobName, String jobGroupName) {  
        JobKey jobKey =JobKey.jobKey(jobName, jobGroupName);  
        try {  
            Scheduler sched = gSchedulerFactory.getScheduler();  
            sched.pauseJob(jobKey);  
        } catch (SchedulerException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
    }  
    /** 
     * @Description:暂停一个任务(使用默认组名) 
     * @param jobName 
     * @param jobGroupName 
     */  
    public static void pauseJob(String jobName) {  
        JobKey jobKey =JobKey.jobKey(jobName, JOB_GROUP_NAME);  
        try {  
            Scheduler sched = gSchedulerFactory.getScheduler();  
            sched.pauseJob(jobKey);  
        } catch (SchedulerException e) {  
            e.printStackTrace();  
        }  
    }  
    /**  
     * @Description:启动所有定时任务  
     */  
    public static void startJobs() {    
        try {    
            Scheduler sched = gSchedulerFactory.getScheduler();    
            sched.start();    
        } catch (Exception e) {    
            throw new RuntimeException(e);    
        }    
    }    
    
    /** 
     * @Description 关闭所有定时任务  
     */  
    public static void shutdownJobs() {    
        try {    
            Scheduler sched = gSchedulerFactory.getScheduler();    
            if (!sched.isShutdown()) {    
                sched.shutdown();    
            }    
        } catch (Exception e) {    
            throw new RuntimeException(e);    
        }    
    }   
    
    /**
     * 查询所有定时任务或者查询指定任务
     * @param jobName 
     * @return
     */
    public static List<ScheduledJob> listJob(String jobName){
    	
    	List<ScheduledJob> jobList = new ArrayList<ScheduledJob>();
    	List<ScheduledJob> jobSingleList = new ArrayList<ScheduledJob>();
    
    	try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
			Set<JobKey> jobkeys = sched.getJobKeys(matcher);
			for(JobKey jobkey:jobkeys){
				List<? extends Trigger> triggers = sched.getTriggersOfJob(jobkey);
				for(Trigger tri :triggers){
					ScheduledJob job = new ScheduledJob();
					
					job.setJobName(jobkey.getName());
					
					job.setJobGroup(jobkey.getGroup());
					job.setDesc("触发："+tri.getKey());
					Trigger.TriggerState status = sched.getTriggerState(tri.getKey());
					job.setJobStatus(status.name());
					if(tri instanceof CronTrigger){
						CronTrigger ctri = (CronTrigger)tri;
						String cronExpress = ctri.getCronExpression();
						job.setCronExpression(cronExpress);
					}
					if(job.getJobName().equals(jobName)){
						jobSingleList.add(job);
					}
					jobList.add(job);
				}
				
			}
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	if(StringUtils.isEmpty(jobName)){
    		return jobList;
    	}else{
    		return jobSingleList;
    	}
    	
		
    	
    }
    
     
     
}