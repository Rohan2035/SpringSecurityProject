package com.rohan.myProject.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rohan.myProject.service.UserService;

//@RestController
//@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
//	@GetMapping("/welcome")
	
	@PostMapping("/allowed-users")
	public String uploadUsers(@RequestPart("file") MultipartFile file) throws IOException {
		
		userService.addAllowedUserstoDb(file);
		
		return "Uploaded successfully !!";
		
	}
	
	@GetMapping("/test")
	public String test() {
		
		return "UP";
		
	}

}
