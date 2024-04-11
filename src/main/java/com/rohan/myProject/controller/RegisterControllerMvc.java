package com.rohan.myProject.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rohan.myProject.entity.UserBuffer;
import com.rohan.myProject.service.RegisterService;
import com.rohan.myProject.utility.MyProjectUrl;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegisterControllerMvc {
	
	@Autowired
	public RegisterService registerService;
	
	@GetMapping("/registration-page")
	public String registrationPage(Model model) {
		
		UserBuffer userBuffer = new UserBuffer();
		model.addAttribute("user", userBuffer);
		
		return "register";
		
	}
	
	@PostMapping("/save")
	public String saveUser(@Valid @ModelAttribute("user") UserBuffer userBuffer, BindingResult result, Model model) throws UnsupportedEncodingException, MessagingException {
		
		String url = MyProjectUrl.BASE_URL + MyProjectUrl.REGISTRATION_BASE_URL;
		
		userBuffer.setRoles("USER");
		
		String status = registerService.addUser(userBuffer, url);
		
		return "redirect:/register/registration-page?"+status;
		
	}
	
	@GetMapping("/verify")
	public String verifyUser(@Param("code") String code) {
		
		if(registerService.verified(code))
			return "success";
		else
			return "error";
		
	}

}
