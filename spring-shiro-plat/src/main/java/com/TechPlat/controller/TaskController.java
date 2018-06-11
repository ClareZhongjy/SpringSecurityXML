package com.TechPlat.controller;

import javax.validation.Valid;

import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.TechPlat.commons.result.PageInfo;
import com.TechPlat.commons.utils.StringUtils;
import com.TechPlat.model.ScheduleJob;
import com.TechPlat.model.Task;
import com.TechPlat.service.ITaskService;
import com.TechPlat.commons.base.BaseController;

/**
 * <p>
 * InnoDB free: 7168 kB 前端控制器
 * </p>
 *
 * @author zjy
 * @since 2018-05-27
 */
@Controller
@RequestMapping("/task")
public class TaskController extends BaseController {

    @Autowired private ITaskService taskService;
    
    
    
    @GetMapping("/manager")
    public String manager() {
        return "task/taskList";
    }
    
    @PostMapping("/dataGrid")
    @ResponseBody
    public PageInfo dataGrid(ScheduleJob task, Integer page, Integer rows, String sort,String order) {
        PageInfo pageInfo = new PageInfo(page, rows, sort, order);
        Map<String, Object> condition = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(task.getJobName())) {
            condition.put("taskName", task.getJobName());
        }
       
        pageInfo.setCondition(condition);
        taskService.selectDataGrid(pageInfo);
        
        return pageInfo;
    }
    
    /**
     * 添加页面
     * @return
     */
    @GetMapping("/addPage")
    public String addPage() {
        return "task/taskAdd";
    }
    
    /**
     * 添加
     * @param 
     * @return
     */
    @PostMapping("/add")
    @ResponseBody
    public Object add(@Valid ScheduleJob task) {
       
        boolean b = taskService.addTask(task);
        if (b) {
            return renderSuccess("添加成功！");
        } else {
            return renderError("添加失败！");
        }
    }
    
    /**
     * 删除
     * @param id
     * @return
     */
    @PostMapping("/delete")
    @ResponseBody
    public Object delete(Long id) {
        Task task = new Task();
        task.setId(id);
      
        boolean b = taskService.updateById(task);
        if (b) {
            return renderSuccess("删除成功！");
        } else {
            return renderError("删除失败！");
        }
    }
    
    /**
     * 编辑
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/editPage")
    public String editPage(Model model, Long id) {
        Task task = taskService.selectById(id);
        model.addAttribute("task", task);
        return "admin/task/taskEdit";
    }
    
    /**
     * 编辑
     * @param 
     * @return
     */
    @PostMapping("/edit")
    @ResponseBody
    public Object edit(@Valid Task task) {
        
        boolean b = taskService.updateById(task);
        if (b) {
            return renderSuccess("编辑成功！");
        } else {
            return renderError("编辑失败！");
        }
    }
}
