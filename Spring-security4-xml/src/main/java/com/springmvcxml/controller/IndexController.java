package com.springmvcxml.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.springmvcxml.entity.User;



@Controller
@RequestMapping("/index")
public class IndexController {

	@RequestMapping("/left")
	public String left(){
		return "left";
	}
	
	@RequestMapping("/top")
	public String top(){
		return "top";
	}
	
	
	
	@RequestMapping("/main")
	public String enterMain(){
		return "plat";
	}
	
	
	@RequestMapping("/welcome")
	public String preWelcome(){
		return "index";
	}
	
	@RequestMapping(value="/plat.do",method=RequestMethod.GET)
	public String Platform(@ModelAttribute User user,HttpSession session){
		
//		logger.info("User: "+user.getUserName());
//		logger.info("Session: "+session.getId());
//		User result = userService.getUserByLogin(user);
//		
//		result.setSession(session.getId());
//		
//		int update = userService.updateUser(result);
//		logger.info("Result: "+result.toString());
//		
//		session.setAttribute(HttpConstants.SESSION_ATTRIBUTE_USER, result);
		
		return "plat";
	}
	
	
}
