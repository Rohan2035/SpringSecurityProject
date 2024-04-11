package com.rohan.myProject.service;

import com.rohan.myProject.entity.ResetPasswordDto;
import com.rohan.myProject.entity.User;

public interface ForgotPasswordService {
	
	public Boolean sendResetLink(String email, String url) throws Exception;
	public User findByCode(String code) throws Exception;
	public Boolean resetPassword(String code, ResetPasswordDto passwordDto) throws Exception;
	
}
