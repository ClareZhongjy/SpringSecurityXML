package com.TechPlat.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.TechPlat.model.ActivitiTask;
import com.TechPlat.model.LeaveApply;
import com.TechPlat.model.Process;
import com.TechPlat.service.ILeaveService;
import com.TechPlat.service.ITaskService;






import com.TechPlat.service.impl.LeaveServiceImpl;

import org.activiti.engine.FormService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.TechPlat.commons.base.BaseController;
import com.TechPlat.commons.result.PageInfo;
import com.TechPlat.commons.shiro.ShiroUser;

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
	
	@Autowired
	ITaskService bizTaskService;
	
	@Autowired
	ILeaveService leaveService;


	
//	private ApplicationContext getApplicationContext(){
//		ApplicationContext apc = new ClassPathXmlApplicationContext("spring-config.xml");
//		return apc;
//	}
	
	
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
			p.setProcessId(pd.getId());
			p.setKey(pd.getKey());
			p.setName(pd.getName());
			p.setResourceName(pd.getResourceName());
			p.setDiagramresourcename(pd.getDiagramResourceName());
			bpmDisplayList.add(p);
		}
		pageInfo.setRows(bpmDisplayList);
		return pageInfo;
	}
	
	@GetMapping("/preUploadBpmFile")
	public String preUploadBpmFile(){
		return "activitiBpm/UploadBpmFile";
		
	}
	@RequestMapping("/uploadBpmFileByMultipart")
	@ResponseBody
	public Object uploadBpmFileByMultipart(@RequestParam MultipartFile bpmFile,HttpServletRequest request) throws IOException{
		long startTime = System.currentTimeMillis();
		String filename = bpmFile.getOriginalFilename();
		//String prefix =  request.getSession().getServletContext().getRealPath("FileDir");
		//File savedFile = new File(prefix,filename);
		//bpmFile.transferTo(savedFile);
		
		//InputStream is = new FileInputStream(savedFile);
		rep.createDeployment().addInputStream(filename, bpmFile.getInputStream()).deploy();
		long endTime = System.currentTimeMillis();
		String times = String.valueOf((endTime-startTime)/1000.00)+"s";
		return renderSuccess("上传成功，耗时"+times);
		
		
	}
	/**
	 * 删除流程
	 * @param processId
	 * @return
	 */
	@RequestMapping("/deleteProcess")
	@ResponseBody
	public Object deleteProcess(String processId){
		try{
			rep.deleteDeployment(processId,true);
			return renderSuccess("删除成功！");
		}catch(Exception e){
			return renderError("删除失败"+e);
		}
		
	}
	@GetMapping("/ApplyTaskList")
	public String getApplyTaskList(){
		return "activitiBpm/ApplyTaskList";
	}
	
	@RequestMapping("preStartLeaveApply")
	public String preStartLeaveApply(){
		return "activitiBpm/LeaveApply";
		
	}
	/**
	 * 开始请假流程	
	 * @param apply
	 * @return
	 */
	@RequestMapping("startLeaveApply")
	@ResponseBody
	public Object startLeaveApply(LeaveApply apply){
		ShiroUser shiroUser = getShiroUser();
		Map<String,Object> variables = new HashMap<String,Object>();
		variables.put("applyuserid", shiroUser.getId());
		ProcessInstance ins = leaveService.startWorkFlow(apply, String.valueOf(shiroUser.getId()), variables);
		System.out.println("流程id"+ins.getId()+"已启动！");
		
		return renderSuccess("启动成功");
		
	}
	
	

}
