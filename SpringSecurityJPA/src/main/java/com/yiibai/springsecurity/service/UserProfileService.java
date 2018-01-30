package com.yiibai.springsecurity.service;

import java.util.List;

import com.yiibai.springsecurity.model.UserProfile;

public interface UserProfileService {

	List<UserProfile> findAll();
	
	UserProfile findByType(String type);
	
	UserProfile findById(int id);
}
