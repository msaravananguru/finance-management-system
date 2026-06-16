package com.finance.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOtp(
            String email,
            String otp) {

        SimpleMailMessage message =
                new SimpleMailMessage();

        message.setTo(email);

        message.setSubject(
                "Finance App OTP Verification");

        message.setText(
                "Your OTP is: " + otp);

        System.out.println("Sending OTP to : " + email);

        mailSender.send(message);

        System.out.println("OTP Sent Successfully");
    }
}