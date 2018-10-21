package com.TechPlat.service.impl;

import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;

import com.TechPlat.model.LeaveApply;
import com.TechPlat.service.ILeaveService;

public class LeaveServiceImpl implements ILeaveService {

	@Override
	public ProcessInstance startWorkFlow(LeaveApply apply, String userId,
			Map<String, Object> variables) {
		
		return null;
	}

}
