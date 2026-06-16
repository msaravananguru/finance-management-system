package com.finance.app.service;

import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.finance.app.entity.EmailOtp;
import com.finance.app.entity.PendingRegistration;
import com.finance.app.repository.EmailOtpRepository;
import com.finance.app.repository.PendingRegistrationRepository;
import com.finance.app.repository.UserRepository;
import com.finance.app.security.JwtUtil;
import com.finance.app.dto.CommonResponse;
import com.finance.app.dto.auth.LoginRequest;
import com.finance.app.dto.auth.LoginResponse;
import com.finance.app.dto.auth.RegisterRequest;
import com.finance.app.dto.otp.VerifyOtpRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.finance.app.entity.User;

@Service
public class AuthService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PendingRegistrationRepository pendingRepository;

	@Autowired
	private EmailOtpRepository emailOtpRepository;

	@Autowired
	private EmailService emailService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtUtil jwtUtil;

	public CommonResponse register(RegisterRequest request) {

		if (userRepository.existsByUsername(request.getUsername())) {

			return new CommonResponse(false, "Username Already Exists", null);
		}

		if (userRepository.existsByEmail(request.getEmail())) {

			return new CommonResponse(false, "Email Already Exists", null);
		}

		if (pendingRepository.existsByUsername(request.getUsername())) {

			return new CommonResponse(false, "Username Already Pending Approval", null);
		}

		if (pendingRepository.existsByEmail(request.getEmail())) {

			return new CommonResponse(false, "Email Already Pending Approval", null);
		}

		PendingRegistration pending = new PendingRegistration();

		pending.setFullName(request.getFullName());

		pending.setUsername(request.getUsername());

		pending.setEmail(request.getEmail());

		pending.setPassword(request.getPassword());

		pending.setRequestedRole(request.getRequestedRole());

		pending.setOtpVerified(false);

		pending.setCreatedDate(LocalDate.now());

		pendingRepository.save(pending);

		String otp = String.valueOf(100000 + new Random().nextInt(900000));

		EmailOtp emailOtp = new EmailOtp();

		emailOtp.setEmail(request.getEmail());

		emailOtp.setOtp(otp);

		emailOtp.setVerified(false);

		emailOtp.setCreatedDate(LocalDateTime.now());

		emailOtp.setExpiryTime(LocalDateTime.now().plusMinutes(10));

		emailOtpRepository.save(emailOtp);

		emailService.sendOtp(request.getEmail(), otp);

		return new CommonResponse(true, "OTP Sent To Email", null);
	}

	public CommonResponse login(LoginRequest request) {

		User user = userRepository.findByUsername(request.getUsername()).orElse(null);

		if (user == null) {

			return new CommonResponse(false, "Invalid Username", null);
		}

		if (!"ACTIVE".equals(user.getStatus())) {

			return new CommonResponse(false, "User Not Approved Yet", null);
		}

		if (Boolean.TRUE.equals(user.getAccountLocked())) {

			return new CommonResponse(false, "Account Locked", null);
		}

		boolean passwordMatched = passwordEncoder.matches(request.getPassword(), user.getPassword());

		if (!passwordMatched) {

			user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);

			if (user.getFailedLoginAttempts() >= 5) {

				user.setAccountLocked(true);
			}

			userRepository.save(user);

			return new CommonResponse(false, "Invalid Password", null);
		}

		user.setFailedLoginAttempts(0);

		userRepository.save(user);

		String token = jwtUtil.generateToken(user.getUsername());

		LoginResponse response = new LoginResponse();

		response.setToken(token);

		response.setFullName(user.getFullName());

		response.setUsername(user.getUsername());

		response.setRole(user.getRole());

		return new CommonResponse(true, "Login Successful", response);
	}

	public CommonResponse verifyOtp(VerifyOtpRequest request) {

		EmailOtp emailOtp = emailOtpRepository.findTopByEmailOrderByIdDesc(request.getEmail()).orElse(null);

		if (emailOtp == null) {

			return new CommonResponse(false, "OTP Not Found", null);
		}

		if (Boolean.TRUE.equals(emailOtp.getVerified())) {

			return new CommonResponse(false, "OTP Already Used", null);
		}

		if (LocalDateTime.now().isAfter(emailOtp.getExpiryTime())) {

			return new CommonResponse(false, "OTP Expired", null);
		}

		if (!emailOtp.getOtp().equals(request.getOtp())) {

			return new CommonResponse(false, "Invalid OTP", null);
		}

		PendingRegistration pending = pendingRepository.findByEmail(request.getEmail()).orElse(null);

		if (pending == null) {

			return new CommonResponse(false, "Pending Registration Not Found", null);
		}

		emailOtp.setVerified(true);

		emailOtpRepository.save(emailOtp);

		User user = new User();

		user.setFullName(pending.getFullName());

		user.setUsername(pending.getUsername());

		user.setEmail(pending.getEmail());

//		user.setPassword(pending.getPassword());

		user.setPassword(passwordEncoder.encode(pending.getPassword()));

		user.setRequestedRole(pending.getRequestedRole());

		user.setIsVerified(true);

		user.setCreatedDate(LocalDate.now());

		user.setFailedLoginAttempts(0);

		user.setAccountLocked(false);

		long userCount = userRepository.count();

		if (userCount == 0) {

			user.setRole("SUPER_ADMIN");

			user.setStatus("ACTIVE");
		} else {

			user.setRole(pending.getRequestedRole());

			user.setStatus("PENDING");
		}

		userRepository.save(user);

		pendingRepository.delete(pending);

		return new CommonResponse(true, "Registration Submitted Successfully", user);
	}
}