package com.spring.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.spring.rest.bean.User;

public interface UserInterface {
	@GET
	@Path("/user/query")
	@Produces(MediaType.APPLICATION_JSON)
	public User queryUserByName(String userName);
}
