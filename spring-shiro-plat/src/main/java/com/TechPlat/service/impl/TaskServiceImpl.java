package com.TechPlat.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TechPlat.commons.result.PageInfo;
import com.TechPlat.mapper.TaskMapper;
import com.TechPlat.model.ScheduleJob;
import com.TechPlat.model.ScheduledJob;
import com.TechPlat.model.Task;
import com.TechPlat.service.ITaskService;

import com.TechPlat.task.TaskManager;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

/**
 * <p>
 * InnoDB free: 7168 kB 服务实现类
 * </p>
 *
 * @author zjy
 * @since 2018-05-27
 */
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements ITaskService {

	@Autowired
	private TaskMapper taskMapper;
	@Autowired
	private TaskManager taskM;

	@Override
	public void selectDataGrid(PageInfo pageInfo) {
		List<ScheduleJob> list = new ArrayList<ScheduleJob>();
		Page<Map<String, Object>> page = new Page<Map<String, Object>>(pageInfo.getNowpage(), pageInfo.getSize());
		page.setOrderByField(pageInfo.getSort());
		page.setAsc(pageInfo.getOrder().equalsIgnoreCase("asc"));
		Object jobName = pageInfo.getCondition().get("taskName");
		if(null!=jobName){
			try {
				list = taskM.getAllJob();
			} catch (SchedulerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			try {
				list = taskM.getAllJob();
			} catch (SchedulerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		pageInfo.setRows(list);
		pageInfo.setTotal(page.getTotal());

	}

	@Override
	public boolean addTask(ScheduledJob job) {
		boolean flag = false;
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		String jobId= sf.format(new Date());
//		QuartzJob qjob = new QuartzJob();
//		qjob.setJobId(jobId);
//		qjob.setJobName(job.getJobName());
//		qjob.setJobTime(jobId);
//		qjob.setJobGroup("test_group");
//		qjob.setJobCron(job.getCronExpression());
//		qjob.setCreTime(jobId);
//		qjob.setDesc(job.getDesc());
//		try {
//			// flag = QuartzManager.addJob( Class.forName(job.getDesc()), job);
//			
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			
//		}
//		
		return flag;
		
	}

}
