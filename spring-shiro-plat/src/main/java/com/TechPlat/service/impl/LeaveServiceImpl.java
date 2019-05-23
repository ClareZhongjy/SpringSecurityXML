package com.TechPlat.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TechPlat.commons.result.PageInfo;
import com.TechPlat.commons.shiro.ShiroUser;
import com.TechPlat.mapper.BpmLeaveapplyMapper;
import com.TechPlat.model.LeaveApply;
import com.TechPlat.service.ILeaveService;

@Service("leaveService")
public class LeaveServiceImpl implements ILeaveService {

	@Autowired
	IdentityService identityService;
	
	@Autowired
	RuntimeService runtimeService;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	BpmLeaveapplyMapper bpmLeaveApplyMapper;
	
	/**
	 * 开始请假流程
	 */
	@Override
	public ProcessInstance startWorkFlow(LeaveApply apply, String userId,
			Map<String, Object> variables) {
		apply.setApplyTime((new Date()).toString());
		apply.setUserId(userId);
		bpmLeaveApplyMapper.insert(apply);
		String bizKey = String.valueOf(apply.getId());
		identityService.setAuthenticatedUserId(bizKey);
		ProcessInstance instance = runtimeService.startProcessInstanceById("leave",bizKey);
		String instanceId = instance.getId();
		apply.setProcessInstanceId(instanceId);
		bpmLeaveApplyMapper.updateById(apply);
		
		
		return instance;
	}

	/**
	 * 获取待处理
	 * @param shiroUser
	 * @param pageInfo
	 * @return
	 */
	public List<LeaveApply> getMyPendingProcess(ShiroUser shiroUser,
			PageInfo pageInfo) {
		List<LeaveApply> resList = new ArrayList<LeaveApply>();
		List<Task> taskList = taskService.createTaskQuery().taskCandidateUser(String.valueOf(shiroUser.getId())).list();
		for(Task task:taskList){
			String processInstanceId = task.getProcessInstanceId();
			ProcessInstance ins = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
			String bizKey = ins.getBusinessKey();
			LeaveApply leaveDto = bpmLeaveApplyMapper.selectById(Integer.valueOf(bizKey));
			leaveDto.setTask(task);
			resList.add(leaveDto);
		}
		return resList;
	}


}
