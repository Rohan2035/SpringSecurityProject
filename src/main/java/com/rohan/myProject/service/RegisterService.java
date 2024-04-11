package com.rohan.myProject.service;

import java.io.UnsupportedEncodingException;

import com.rohan.myProject.entity.UserBuffer;

import jakarta.mail.MessagingException;

public interface RegisterService {
	
	public String addUser(UserBuffer userBuffer, String url) throws UnsupportedEncodingException, MessagingException;
	
	public boolean verified(String verificationCode);

}
