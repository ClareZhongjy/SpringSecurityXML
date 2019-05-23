package com.TechPlat.controller;

import com.TechPlat.commons.base.BaseController;
import com.TechPlat.commons.result.PageInfo;
import com.TechPlat.commons.shiro.ShiroUser;
import com.TechPlat.model.ActivitiTask;
import com.TechPlat.model.LeaveApply;
import com.TechPlat.service.ILeaveService;
import com.TechPlat.service.ITaskService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/approve")
@Controller
public class ApproveController extends BaseController {
    @Autowired
    ITaskService bizTaskService;

    @Autowired
    ILeaveService leaveService;
    @PostMapping("/processApprove")
    @ResponseBody
    public Object processApprove(@PathVariable("taskId") String taskId){
        try{
            ShiroUser shiroUser = getShiroUser();
            Map<String,Object> variables = new HashMap<String,Object>();
            bizTaskService.processApprove(taskId,shiroUser,variables);
            return renderSuccess("审批成功！");
        }catch(Exception e){
            return renderError("审批失败！"+e.toString());
        }
    }

    /**
     * 查询待办
     */
    @PostMapping("/dataGrid")
    @ResponseBody
    public PageInfo getMyPendingProcess(Integer page, Integer rows, String sort, String order){
        PageInfo pageInfo = new PageInfo(page, rows, sort, order);
        try{
            boolean permissionFlag = false;
            ShiroUser shiroUser = getShiroUser();
//			Set<String> roles = shiroUser.getRoles();
//			Iterator<String> roleSets = roles.iterator();
//			while(roleSets.hasNext()){
//				String role = roleSets.next();
//				if(role.equals(""))
//			}
            List<ActivitiTask> taskList = new ArrayList<ActivitiTask>();
            //if("leave".equals(bizType)){
            //ApplicationContext apc = getApplicationContext();

            List<LeaveApply> leaveProcess = leaveService.getMyPendingProcess(shiroUser,pageInfo);
            for(LeaveApply apply:leaveProcess){
                ActivitiTask task = new ActivitiTask();
                BeanUtils.copyProperties(apply, task);
                taskList.add(task);

            }
            //}
            pageInfo.setRows(taskList);

        }catch(Exception e){
            e.toString();
        }
        return pageInfo;
    }

    @GetMapping("/preProcessApprove")
    public String preProcessApprove(){
        return "bpm/preProcessApprove";
    }
    
    @GetMapping("/preMyPending")
    public String preMyPending(){
    	return "activitiBpm/MyProcessTodo";
    }
}
