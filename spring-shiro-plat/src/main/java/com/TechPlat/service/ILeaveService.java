package com.TechPlat.service;

import java.util.List;
import java.util.Map;

import com.TechPlat.commons.result.PageInfo;
import org.activiti.engine.runtime.ProcessInstance;

import com.TechPlat.commons.shiro.ShiroUser;
import com.TechPlat.model.LeaveApply;

public interface ILeaveService {
	public ProcessInstance startWorkFlow(LeaveApply apply,String userId,Map<String,Object> variables);

	List<LeaveApply> getMyPendingProcess(ShiroUser shiroUser,
										 PageInfo pageInfo);
}
