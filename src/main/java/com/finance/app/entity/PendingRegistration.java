package com.finance.app.entity;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "pending_registration")
public class PendingRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private String username;

    private String email;

    private String password;

    private String requestedRole;

    private Boolean otpVerified;

    private LocalDate createdDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(
            String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(
            String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(
            String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(
            String password) {
        this.password = password;
    }

    public String getRequestedRole() {
        return requestedRole;
    }

    public void setRequestedRole(
            String requestedRole) {
        this.requestedRole = requestedRole;
    }

    public Boolean getOtpVerified() {
        return otpVerified;
    }

    public void setOtpVerified(
            Boolean otpVerified) {
        this.otpVerified = otpVerified;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(
            LocalDate createdDate) {
        this.createdDate = createdDate;
    }
}