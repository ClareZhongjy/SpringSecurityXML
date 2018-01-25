package com.springmvcxml.dao;

import java.util.List;

import com.springmvcxml.entity.UserRole;

public interface UserRoleMapper {
	public List<String> getAuthoritiesByName(String username);
	
	public void saveUserRole(UserRole userRole);
}
