package com.TechPlat.controller;

import java.util.ArrayList;
import java.util.List;
import com.TechPlat.model.Process;
import org.activiti.engine.FormService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.TechPlat.commons.base.BaseController;
import com.TechPlat.commons.result.PageInfo;

@Controller
@RequestMapping("/bpm")
public class BpmController extends BaseController {
	
	@Autowired
	RepositoryService rep;
	@Autowired
	RuntimeService runservice;
	@Autowired
	FormService formservice;
	@Autowired
	IdentityService identityservice;
	
	
	@GetMapping("/processList")
	public String processList(){
		return "activitiBpm/processList";
	}
	
	@PostMapping("/dataGrid")
    @ResponseBody
	public PageInfo dataGrid(Integer page, Integer rows, String sort,String order){
		
		PageInfo pageInfo = new PageInfo(page, rows, sort, order);
		List<ProcessDefinition> bpmList = rep.createProcessDefinitionQuery().list();
		List<Process> bpmDisplayList = new ArrayList<Process>();
		for(ProcessDefinition pd:bpmList){
			Process p = new Process();
			p.setDeploymentId(pd.getDeploymentId());
			p.setId(pd.getId());
			p.setKey(pd.getKey());
			p.setName(pd.getName());
			p.setResourceName(pd.getResourceName());
			p.setDiagramresourcename(pd.getDiagramResourceName());
			bpmDisplayList.add(p);
		}
		pageInfo.setRows(bpmDisplayList);
		return pageInfo;
	}
	
}
