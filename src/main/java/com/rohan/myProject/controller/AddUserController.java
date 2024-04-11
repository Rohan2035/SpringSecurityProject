package com.rohan.myProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rohan.myProject.entity.User;
import com.rohan.myProject.service.AddUserService;

@RestController
@RequestMapping("/add-users")
public class AddUserController {

	@Autowired
	private AddUserService addUserService;

	@PostMapping("/add-user")
	public ResponseEntity<String> addUser(@RequestBody User user) {
		
		addUserService.addUser(user);

		return new ResponseEntity<String>("User Successfully Added !!", HttpStatus.OK);

	}
	
	@GetMapping("/test")
	public String test() {
		
		return "UP";
		
	}

}
