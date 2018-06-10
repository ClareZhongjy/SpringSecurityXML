package com.TechPlat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.TechPlat.commons.base.BaseController;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends BaseController{
	
	@GetMapping("/index")
	public String welcome(){
		return "welcome/index";
	}
	
}
