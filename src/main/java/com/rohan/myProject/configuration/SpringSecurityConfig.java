package com.rohan.myProject.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
	
	@Autowired
	private LoginSuccessHandler loginSuccessHandler;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		
		httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.cors(AbstractHttpConfigurer::disable);
		
		httpSecurity
			.authorizeHttpRequests(authorize -> { 
				authorize.requestMatchers("/").hasAnyRole("USER", "ADMIN");
				authorize.requestMatchers("/register/**").permitAll();
				authorize.requestMatchers("/forgot-password/**").permitAll();
				authorize.requestMatchers("/users/**").hasRole("ADMIN");
				authorize.anyRequest().permitAll();
			})
			.httpBasic(Customizer.withDefaults())
			.formLogin((login) -> {
				
				login.loginPage("/login");
				login.successHandler(loginSuccessHandler);
				
			});
		
		return httpSecurity.build();
		
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
		
	}

}
