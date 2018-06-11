package com.TechPlat.task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.TechPlat.model.ScheduleJob;
import com.TechPlat.model.ScheduledJob;

public class QuartzFactory implements Job {
	
	private Logger logger = LogManager.getLogger(getClass()); 

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		logger.info("--------start scheduleJob --------");
		try{
			ScheduleJob scheduledJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
			TaskUtils.invokMethod(scheduledJob);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		logger.info("----------scheduleJob end---------");
		
	}

}
