package com.rohan.myProject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResetPasswordDto {
	
	private String code;
	
	private String passwordOne;
	
	private String passwordTwo;
	
}
