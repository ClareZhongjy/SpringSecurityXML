package com.TechPlat.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.TechPlat.model.ScheduleJob;


@Service
public class TaskManager {
	private Logger logger = LogManager.getLogger(getClass());
	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;

	private static SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();
	
	private static String JOB_GROUP_NAME = "Default_Group";
	
	private static String TRIGGER_GROUP_NAME = "Default_Trigger_Group";

	@SuppressWarnings("unchecked")
	public  boolean addJob(Class cls, ScheduleJob scheduleJob) {
		try {
			Scheduler sched = schedulerFactoryBean.getScheduler();

			String jobName = scheduleJob.getJobName();
			String cronExpress = scheduleJob.getCronExpression();
			String jobGroup = scheduleJob.getJobGroup();
		
			if(StringUtils.isEmpty(jobGroup)){
				jobGroup = JOB_GROUP_NAME;
			}
			
			JobKey jobKey = new JobKey(jobName, jobGroup);
			
			JobDetail jobDetail = JobBuilder.newJob(cls).withIdentity(jobKey).build();

			TriggerKey triggerKey = new TriggerKey(jobName, TRIGGER_GROUP_NAME);
			
			CronScheduleBuilder schedBuilder = CronScheduleBuilder.cronSchedule(cronExpress);
			
			Trigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(schedBuilder).build();

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


	@PostConstruct
	public void init() throws Exception {

		Scheduler scheduler = gSchedulerFactory.getScheduler();
		GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
		Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
		
		for (JobKey jobKey : jobKeys) {
			List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
			for (Trigger trigger : triggers) {
			
				if (trigger instanceof CronTrigger) {
					CronTrigger cronTrigger = (CronTrigger) trigger;
					//一定要先启动scheduler，不然无法触发
					scheduler.start();
					scheduler.rescheduleJob(cronTrigger.getKey(), cronTrigger);
				}
				
			}
		}
	
	}

	/**
	 * 获取所有计划中的任务列表
	 * 
	 * @return
	 * @throws SchedulerException
	 */
	public List<ScheduleJob> getAllJob(String jobName) throws SchedulerException {
		List<ScheduleJob> jobList = new ArrayList<ScheduleJob>();
		List<ScheduleJob> jobSingleList = new ArrayList<ScheduleJob>();

		try {
			Scheduler sched = schedulerFactoryBean.getScheduler();
			GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
			Set<JobKey> jobkeys = sched.getJobKeys(matcher);
			for (JobKey jobkey : jobkeys) {
				List<? extends Trigger> triggers = sched.getTriggersOfJob(jobkey);
				for (Trigger tri : triggers) {
					ScheduleJob job = new ScheduleJob();

					job.setJobName(jobkey.getName());

					job.setJobGroup(jobkey.getGroup());
					job.setDescription("触发：" + tri.getKey());
					Trigger.TriggerState status = sched.getTriggerState(tri.getKey());
					job.setJobStatus(status.name());
					if (tri instanceof CronTrigger) {
						CronTrigger ctri = (CronTrigger) tri;
						String cronExpress = ctri.getCronExpression();
						job.setCronExpression(cronExpress);
					}
					if (job.getJobName().equals(jobName)) {
						jobSingleList.add(job);
					}
					jobList.add(job);
				}

			}
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (StringUtils.isEmpty(jobName)) {
			return jobList;
		} else {
			return jobSingleList;
		}

	}

	/**
	 * 所有正在运行的job
	 * 
	 * @return
	 * @throws SchedulerException
	 */
	public List<ScheduleJob> getRunningJob() throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
		List<ScheduleJob> jobList = new ArrayList<ScheduleJob>(executingJobs.size());
		for (JobExecutionContext executingJob : executingJobs) {
			ScheduleJob job = new ScheduleJob();
			JobDetail jobDetail = executingJob.getJobDetail();
			JobKey jobKey = jobDetail.getKey();
			Trigger trigger = executingJob.getTrigger();
			job.setJobName(jobKey.getName());
			job.setJobGroup(jobKey.getGroup());
			job.setDescription("触发器:" + trigger.getKey());
			Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
			job.setJobStatus(triggerState.name());
			if (trigger instanceof CronTrigger) {
				CronTrigger cronTrigger = (CronTrigger) trigger;
				String cronExpression = cronTrigger.getCronExpression();
				job.setCronExpression(cronExpression);
			}
			jobList.add(job);
		}
		return jobList;
	}

	/**
	 * 暂停一个job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void pauseJob(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.pauseJob(jobKey);
	}

	/**
	 * 恢复一个job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void resumeJob(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.resumeJob(jobKey);
	}

	/**
	 * 删除一个job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void deleteJob(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.deleteJob(jobKey);

	}

	/**
	 * 立即执行job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void runAJobNow(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.triggerJob(jobKey);
	}

	/**
	 * 更新job时间表达式
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void updateJobCron(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();

		TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());

		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());

		trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

		scheduler.rescheduleJob(triggerKey, trigger);
	}

	public void StartTask(ScheduleJob job) {
		try {
			Scheduler scheduler = gSchedulerFactory.getScheduler();
			// Trigger已存在，那么更新相应的定时设置
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
			
			TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
			// 按新的cronExpression表达式重新构建trigger
			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

			// 按新的trigger重新设置job执行
			scheduler.rescheduleJob(triggerKey, trigger);
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
