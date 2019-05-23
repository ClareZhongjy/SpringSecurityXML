package com.TechPlat.service;


import java.util.Map;

import com.TechPlat.commons.result.PageInfo;
import com.TechPlat.commons.shiro.ShiroUser;
import com.TechPlat.model.ScheduleJob;
import com.TechPlat.model.Task;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * InnoDB free: 7168 kB 服务类
 * </p>
 *
 * @author zjy
 * @since 2018-05-27
 */
public interface ITaskService extends IService<Task> {

	
	void selectDataGrid(PageInfo pageInfo);
	
	boolean addTask(ScheduleJob job);
	
	boolean deleteTask(ScheduleJob job);
	
	boolean startTask(ScheduleJob job);
	
	boolean editTask(ScheduleJob job);
	
	void processApprove(String taskId,ShiroUser shiroUser,Map<String,Object> variables);
}
