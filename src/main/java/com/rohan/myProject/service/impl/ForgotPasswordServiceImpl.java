package com.rohan.myProject.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rohan.myProject.entity.ResetPasswordDto;
import com.rohan.myProject.entity.User;
import com.rohan.myProject.entity.UserBuffer;
import com.rohan.myProject.repository.UserRepository;
import com.rohan.myProject.service.ForgotPasswordService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Boolean sendResetLink(String email, String url) throws Exception {

		Optional<User> userOptional = userRepository.findByEmail(email);

		if (userOptional.isPresent()) {

			User user = userOptional.get();

			String resetPasswordToken = RandomStringUtils.randomAlphabetic(9);
			user.setPassword(resetPasswordToken);
			userRepository.save(user);

			sendVerificationCode(user, url);

			return true;
			
		} else {
			
			return false;
			
		}
		
	}

	public void sendVerificationCode(User user, String url) throws UnsupportedEncodingException, MessagingException {

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		String toAddress = user.getEmail();
		String fromAddress = "rohanvenkateshgupta.12@gmail.com";
		String subject = "Reset Your Password";
		String content = "Hi, <br>" + "Please click on the link below to reset your password: <br>"
				+ "<h3><a href=\"[[URL]]\" target=\"_self\">RESET</a></h3>" + "Thank you,<br>";

		String resetURL = url + "/reset-password?code=" + user.getPassword();
		content = content.replace("[[URL]]", resetURL);

		helper.setFrom(fromAddress, "myProject");
		helper.setTo(toAddress);
		helper.setSubject(subject);
		helper.setText(content, true);

		mailSender.send(message);

	}

	@Override
	public User findByCode(String code) throws Exception {

		User user = userRepository.findByPassword(code).orElseThrow(() -> new Exception());

		return user;

	}

	@Override
	public Boolean resetPassword(String code, ResetPasswordDto passwordDto) throws Exception {

		User user = userRepository.findByPassword(code).orElseThrow(() -> new Exception());

		user.setPassword(passwordEncoder.encode(passwordDto.getPasswordOne()));

		userRepository.save(user);

		return true;

	}

}
