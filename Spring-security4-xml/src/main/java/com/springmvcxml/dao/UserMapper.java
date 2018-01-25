package com.springmvcxml.dao;



import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.springmvcxml.entity.User;


public interface UserMapper {
	public User getUserByLogin(User user);

	public int updateUser(User user);

	public User findUserByName(@Param("userName")String userName) throws DataAccessException;

	public void saveUser(User user);
}
