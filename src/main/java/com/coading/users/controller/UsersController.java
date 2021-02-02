package com.coading.users.controller;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.coading.users.service.UserService;

@RestController
public class UsersController {
	
	@Autowired
	UserService userService;

	@GetMapping("/user/{id}")
	public String getFollowerOfUsers(@PathVariable String id) throws ParseException {
		System.out.println("Entered in Controller");
		return userService.getFollowersList(id);
		 
	}
}
