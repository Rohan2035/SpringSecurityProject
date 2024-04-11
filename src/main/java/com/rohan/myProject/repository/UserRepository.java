package com.rohan.myProject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rohan.myProject.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
	
	public Optional<User> findByUsernameOrEmail(String username, String email);
	
	public Optional<User> findByEmail(String email);
	
	public Optional<User> findByPassword(String password);

}
