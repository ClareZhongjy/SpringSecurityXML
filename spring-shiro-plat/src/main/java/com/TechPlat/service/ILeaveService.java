package com.TechPlat.service;

import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;

import com.TechPlat.model.LeaveApply;

public interface ILeaveService {
	public ProcessInstance startWorkFlow(LeaveApply apply,String userId,Map<String,Object> variables);
}
