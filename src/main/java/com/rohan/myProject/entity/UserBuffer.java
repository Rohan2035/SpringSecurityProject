package com.rohan.myProject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserBuffer {
	
	@Id
	@NotNull
	@Column(unique = true)
	private String username;
	
	@NotNull
	@Email
	private String email;
	
	@NotNull
	@Size(min = 4, message = "Enter at least 4 Characters")
	private String password;
	
	@NotNull
	private String roles;
	
	@NotNull
	private String firstname;
	
	private String lastname;
	
	private String designation;
	
	@Column(name = "verification_code", length = 64)
	private String verificationCode;

}
