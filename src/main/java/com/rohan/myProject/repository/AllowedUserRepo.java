package com.rohan.myProject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rohan.myProject.entity.AllowedUser;

public interface AllowedUserRepo extends JpaRepository<AllowedUser, Integer> {
	
	public Optional<AllowedUser> findByUsername(String username);

}
