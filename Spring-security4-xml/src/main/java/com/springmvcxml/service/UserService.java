package com.springmvcxml.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.springmvcxml.dao.UserMapper;
import com.springmvcxml.dao.UserRoleMapper;
import com.springmvcxml.entity.User;
import com.springmvcxml.entity.UserRole;

@Service("userService")
public class UserService {
	
	@Autowired
	UserMapper userDao;
	
	@Autowired
	UserRoleMapper userRoleDao;
	
	@Autowired
	private PasswordEncoder encoder;
	
	public User getUserByLogin(User user) {
		User result = userDao.getUserByLogin(user);
		return result;
	}
	
	
	
	public User findUserByName(String username) {
		User result = userDao.findUserByName(username);
		return result;
	}



	public void saveUser(User user) {
		String password = encoder.encode(user.getPassword());
		user.setPassword(password);
		String role = "";
		List<String> roleList = user.getRoleList();
		for(String r:roleList){
			role =role+","+r;
		}
		role = role.substring(1);
		System.out.println(role);
		user.setRole(role);
		userDao.saveUser(user);
		
		

	}
	

	
}
