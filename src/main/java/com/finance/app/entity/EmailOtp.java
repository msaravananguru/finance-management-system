package com.finance.app.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "email_otp")
public class EmailOtp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String otp;

    private LocalDateTime expiryTime;

    private Boolean verified;

    private LocalDateTime createdDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(
            String email) {
        this.email = email;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(
            String otp) {
        this.otp = otp;
    }

    public LocalDateTime getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(
            LocalDateTime expiryTime) {
        this.expiryTime = expiryTime;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(
            Boolean verified) {
        this.verified = verified;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(
            LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}