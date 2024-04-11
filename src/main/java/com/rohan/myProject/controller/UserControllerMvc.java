package com.rohan.myProject.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.rohan.myProject.entity.User;
import com.rohan.myProject.service.UserService;

@Controller
@RequestMapping("/users")
public class UserControllerMvc {

	@Autowired
	private UserService userService;

	@GetMapping("/upload-excel")
	public String uploadExcel(Model model) {
		
		return "upload_form";

	}

	@PostMapping("/uploaded")
	public String upload(Model model, @RequestPart("file") MultipartFile file) throws IOException {
		
		if(userService.addAllowedUserstoDb(file)) {
			
			String message = "File Uploaded Successfully: " + file.getOriginalFilename();
		    model.addAttribute("message", message);
			
		} else {
			
			String message = "File Can't Be Uploaded !!";
		    model.addAttribute("message", message);
			
		}
		
		return "upload_form";
		
	}

}
