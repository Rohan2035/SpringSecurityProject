package com.rohan.myProject.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.rohan.myProject.entity.User;

public interface UserService {
	
	public boolean addAllowedUserstoDb(MultipartFile file) throws IOException;
	
	public List<User> getUsersList();

}
