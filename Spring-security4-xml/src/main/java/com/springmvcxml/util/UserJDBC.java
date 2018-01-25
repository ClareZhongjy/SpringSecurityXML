package com.springmvcxml.util;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.springmvcxml.entity.User;

public class UserJDBC {
	
	private static String url="jdbc:mysql://localhost:3306/test";
	private static String dbUser = "root";
	private static String dbPass = "password";
	
	
	public User getUserInfo(String username) throws SQLException{
		User user = new User();
		Connection conn = null;
		try {
			conn = (Connection)DriverManager.getConnection(url,dbUser,dbPass);
			String sql="select username,password,age,session,cnName from user where username='"+username+"'";
			PreparedStatement psmt = conn.prepareStatement(sql);
			ResultSet rs = psmt.executeQuery();
			while(rs.next()){
				user.setUserName(rs.getString(1));
				user.setPassword(rs.getString(2));
				
				user.setAge(Integer.parseInt(rs.getString(3)));
				user.setSession(rs.getString(4));
				user.setCnName(rs.getString(5));
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			conn.close();
		}
		
		return user;
		
	}
	
	public List<String> getAuthoritiesByName(String username){
		
		List<String> roles = new ArrayList<String>();
		Connection conn = null;
		try {
			conn = (Connection)DriverManager.getConnection(url,dbUser,dbPass);
			String sql="select type from user_role where username='"+username+"'";
			PreparedStatement psmt = conn.prepareStatement(sql);
			ResultSet rs = psmt.executeQuery();
			while(rs.next()){
				roles.add(rs.getString(1));
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return roles;
		
	}
}
