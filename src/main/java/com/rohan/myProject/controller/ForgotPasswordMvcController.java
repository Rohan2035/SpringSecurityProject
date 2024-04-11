package com.rohan.myProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rohan.myProject.entity.ResetPasswordDto;
import com.rohan.myProject.entity.User;
import com.rohan.myProject.entity.UserBuffer;
import com.rohan.myProject.repository.UserRepository;
import com.rohan.myProject.service.ForgotPasswordService;
import com.rohan.myProject.service.UserService;
import com.rohan.myProject.utility.MyProjectUrl;

@Controller
@RequestMapping("/forgot-password")
public class ForgotPasswordMvcController {
	
	@Autowired
	public ForgotPasswordService forgotPasswordService;
	
	@GetMapping("")
	public String forgotPassword(Model model) {
		
		UserBuffer user = new UserBuffer();
		
		model.addAttribute("user", user);
		
		return "forgot_password";
		
	}
	
	@PostMapping("/send-reset-link")
	public String forgotPassowrdRequest(@ModelAttribute("user") UserBuffer userBuffer) throws Exception {
		
		String url = MyProjectUrl.BASE_URL + MyProjectUrl.FORGOT_PASSWORD_BASE_URL;
		
		if(forgotPasswordService.sendResetLink(userBuffer.getEmail(), url))
			return "redirect:/forgot-password?success";
		else 
			return "redirect:/forgot-password?error";
	}
	
	@GetMapping("/reset-password")
	public String resetPassword(@Param("code") String code, Model model) throws Exception{
		
		User user = forgotPasswordService.findByCode(code);
		
		if(user.getPassword().equals(code)) {
			
			ResetPasswordDto resetPasswordDto = new ResetPasswordDto();
			resetPasswordDto.setCode(code);
			model.addAttribute("password", resetPasswordDto);
			
			return "reset_password";
			
		}
	
		return "error";
		
	}
	
	@PostMapping("/save")
	public String resetPasswordSave(@ModelAttribute("password") ResetPasswordDto passwordDto) throws Exception {
		
		forgotPasswordService.resetPassword(passwordDto.getCode(), passwordDto);
		
		return "reset_success";
		
	}

}
