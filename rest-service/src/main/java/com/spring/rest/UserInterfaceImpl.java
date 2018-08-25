package com.spring.rest;

import org.springframework.stereotype.Component;

import com.spring.rest.bean.User;

@Component
public class UserInterfaceImpl implements UserInterface{

	public User queryUserByName(String userName) {
		User user = new User();
		user.setName("王浩");
		return user;
	}

}
