package com.rohan.myProject.configuration;

import java.io.IOException;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.rohan.myProject.entity.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
		
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		
		String redirectUrl = request.getContextPath();
		
		Set<GrantedAuthority> authorities = (Set<GrantedAuthority>) userDetails.getAuthorities();
		
		if(authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
			
			redirectUrl = "/users/upload-excel";
			
		} else {
			
			redirectUrl = "/";
			
		}
		
		response.sendRedirect(redirectUrl);
		
	}

}
