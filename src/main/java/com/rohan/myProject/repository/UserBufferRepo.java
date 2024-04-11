package com.rohan.myProject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rohan.myProject.entity.UserBuffer;

public interface UserBufferRepo extends JpaRepository<UserBuffer, String>{
	
	public Optional<UserBuffer> findByUsername(String username);
	
	public Optional<UserBuffer> findByVerificationCode(String verificationCode);

}
