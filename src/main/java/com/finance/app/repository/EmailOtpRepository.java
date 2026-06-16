package com.finance.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finance.app.entity.EmailOtp;

public interface EmailOtpRepository
        extends JpaRepository<EmailOtp, Long> {

    Optional<EmailOtp> findTopByEmailOrderByIdDesc(
            String email);
}