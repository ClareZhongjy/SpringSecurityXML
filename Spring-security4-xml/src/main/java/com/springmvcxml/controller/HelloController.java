package com.springmvcxml.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.springmvcxml.entity.User;

import com.springmvcxml.service.UserService;

@Controller
public class HelloController {
	
	@Autowired
	UserService userService;
	
	
	@RequestMapping(value={"/","/home"},method=RequestMethod.GET)
	public String homePage(ModelMap model){
		model.addAttribute("user", getPrincipal());
		return "home";
		
	}
	@RequestMapping(value="/admin",method=RequestMethod.GET)
	public String adminPage(ModelMap model){
		model.addAttribute("user", getPrincipal());
		return "admin";
		
	}
	
	@RequestMapping(value="/dba",method=RequestMethod.GET)
	public String dbaPage(ModelMap model){
		model.addAttribute("user", getPrincipal());
		return "dba";
		
	}
	@RequestMapping(value="/accessDenied",method=RequestMethod.GET)
	public String accessDenied(ModelMap model){
		
		model.addAttribute("user", getPrincipal());
		return "accessDenied";
		
	}
	
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public String logoutPage(HttpServletRequest request,HttpServletResponse response){
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth !=null){
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:login?logout";
		
	}
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String loginPage(){
		
		return "login";
		
	}
	
	@RequestMapping(value="/test",method=RequestMethod.GET)
	public String test(){
		User a = new User();
		a.setUserName("user");
		a.setPassword("password");
		User b = userService.getUserByLogin(a);
		System.out.println(b.getCnName());
		
		 User temp = userService.findUserByName("user");
		 System.out.println(temp.getCnName());
		return "test";
		
	}
	

	private String getPrincipal() {
		String username = "";
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal instanceof UserDetails){
			username = ((UserDetails)principal).getUsername();
		}else{
			username = principal.toString();
		}
		return username;
	}
	
	@RequestMapping(value="/preNewUser",method=RequestMethod.GET)
	public String preNewUser(ModelMap model){
		User user = new User();
		model.addAttribute("user", user);
		return "newuser";
		
	}
	
	
	
	@RequestMapping(value="/newUser",method=RequestMethod.POST)
	public String saveNewUser(@ModelAttribute User user,ModelMap model){
		
		
		userService.saveUser(user);
		
		return "registrationsuccess";
		
	}
}
