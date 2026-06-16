package com.finance.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finance.app.dto.CommonResponse;
import com.finance.app.dto.auth.LoginRequest;
import com.finance.app.dto.auth.RegisterRequest;
import com.finance.app.dto.otp.VerifyOtpRequest;
import com.finance.app.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public CommonResponse register(
            @RequestBody RegisterRequest request) {

        return authService.register(request);
    }

    @PostMapping("/login")
    public CommonResponse login(
            @RequestBody LoginRequest request) {

        return authService.login(request);
    }
    
    @PostMapping("/verify-otp")
    public CommonResponse verifyOtp(
            @RequestBody VerifyOtpRequest request) {

        return authService.verifyOtp(request);
    }
}