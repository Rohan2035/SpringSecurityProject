package com.rohan.myProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rohan.myProject.entity.User;
import com.rohan.myProject.repository.UserRepository;

@Controller
public class HomeController {
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/")
	public String welcome(Model model, Authentication authentication) {
		
		User user = userRepository.findByUsernameOrEmail(authentication.getName(), authentication.getName())
				.orElseThrow(() -> new UsernameNotFoundException("User not found !!"));
		
		
		model.addAttribute("firstname", user.getFirstname());
		
		return "index";
		
	}

}
