package com.rohan.myProject.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rohan.myProject.entity.User;
import com.rohan.myProject.repository.UserRepository;
import com.rohan.myProject.service.AddUserService;

@Service
public class AddUserServiceImpl implements AddUserService {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void addUser(User user) {
		
		String password = user.getPassword();
		
		user.setPassword(passwordEncoder.encode(password));
		
		userRepository.save(user);
		
	}

}
