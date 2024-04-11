package com.rohan.myProject.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rohan.myProject.entity.AllowedUser;
import com.rohan.myProject.entity.User;
import com.rohan.myProject.entity.UserBuffer;
import com.rohan.myProject.repository.AllowedUserRepo;
import com.rohan.myProject.repository.UserBufferRepo;
import com.rohan.myProject.repository.UserRepository;
import com.rohan.myProject.service.RegisterService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class RegisterServiceImpl implements RegisterService {

	@Autowired
	private AllowedUserRepo allowedUserRepo;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserBufferRepo userBufferRepo;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public String addUser(UserBuffer info, String url) throws UnsupportedEncodingException, MessagingException {

		if (allowedUserRepo.findByUsername(info.getUsername()).isEmpty()) {

			return "error";

		}

		Optional<UserBuffer> userBufferOptional = userBufferRepo.findByUsername(info.getUsername());

		if (!userBufferOptional.isEmpty()) {

			UserBuffer userBuffer = userBufferOptional.get();

			sendVerificationCode(userBuffer, url);

			return "verify";

		}

		String verificationCode = RandomStringUtils.randomAlphabetic(9);

		info.setVerificationCode(verificationCode);

		userBufferRepo.save(info);

		sendVerificationCode(info, url);

		return "success";

	}

	public void sendVerificationCode(UserBuffer userBuffer, String url)
			throws UnsupportedEncodingException, MessagingException {

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		String toAddress = userBuffer.getEmail();
		String fromAddress = "rohanvenkateshgupta.12@gmail.com";
		String subject = "Please Verify Your Registration";
		String content = "Hi [[name]],<br>" + "Please click the link below to verify your registration:<br>"
				+ "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>" + "Thank you,<br>";

		content = content.replace("[[name]]", userBuffer.getFirstname());
		String verifyURL = url + "/verify?code=" + userBuffer.getVerificationCode();
		content = content.replace("[[URL]]", verifyURL);

		helper.setFrom(fromAddress, "myProject");
		helper.setTo(toAddress);
		helper.setSubject(subject);
		helper.setText(content, true);

		mailSender.send(message);

	}

	@Override
	public boolean verified(String verificationCode) {

		Optional<UserBuffer> userBufferOptional = userBufferRepo.findByVerificationCode(verificationCode);

		if (userBufferOptional.isPresent()) {
			
			UserBuffer userBuffer = userBufferOptional.get();

			User user = new User();

			user.setUsername(userBuffer.getUsername());
			user.setPassword(passwordEncoder.encode(userBuffer.getPassword()));
			user.setEmail(userBuffer.getEmail());
			user.setDesignation(userBuffer.getDesignation());
			user.setRoles(userBuffer.getRoles());
			user.setFirstname(userBuffer.getFirstname());
			user.setLastname(userBuffer.getLastname());

			userRepository.save(user);

			userBufferRepo.delete(userBuffer);

			return true;
		}
		
		return false;
		
	}

}
