package com.springmvcxml.entity;

public enum UserRoleType {
	USER("USER"),
	DBA("DBA"),
	ADMIN("ADMIN");
	
	String userProfileType;
	
	private UserRoleType(String userProfileType){
		this.userProfileType = userProfileType;
	}
	
	public String getUserRoleType(){
		return userProfileType;
	}
}
