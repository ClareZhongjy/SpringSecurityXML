package com.frame.blog.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.frame.blog.domain.ContentDO;
import com.frame.blog.service.ContextService;
import com.frame.utils.PageUtils;
import com.frame.utils.Query;

@RequestMapping("/blog")
@Controller
public class BlogController {
	
	@Autowired
	ContextService bContextService;
	
	@GetMapping()
	public String blog(){
		return "blog/index/main";
		
	}
	
	@ResponseBody
	@GetMapping("/open/list")
	public PageUtils openList(@RequestParam Map<String,Object> params){
		Query query = new Query(params);
		List<ContentDO> contentList = bContextService.list(params);
		int total = bContextService.count(query);
		PageUtils pg = new PageUtils(total,contentList);
		return pg;
		
	}
}
