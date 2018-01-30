package com.yiibai.springsecurity.dao;

import com.yiibai.springsecurity.model.User;

public interface UserDao {

	void save(User user);
	
	User findById(int id);
	
	User findBySSO(String sso);
	
}

