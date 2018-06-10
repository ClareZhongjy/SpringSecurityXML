package com.TechPlat.service;


import com.TechPlat.commons.result.PageInfo;
import com.TechPlat.model.ScheduledJob;
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
	
	boolean addTask(ScheduledJob job);
}
