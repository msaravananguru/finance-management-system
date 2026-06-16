package com.finance.app.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    
    private String fullName;

    private String email;

    private String password;

    private String role;

    private String requestedRole;

    private String status;

    private Boolean isVerified;

    private LocalDate createdDate;

    private String approvedBy;

    private LocalDate approvedDate;

    private String approvalRemarks;

    private Integer failedLoginAttempts;

    private Boolean accountLocked;

    private LocalDateTime lastLoginDate;

    private String lastLoginIp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getRole() {
        return role;
    }

    public void setRole(
            String role) {
        this.role = role;
    }

    public String getRequestedRole() {
        return requestedRole;
    }

    public void setRequestedRole(
            String requestedRole) {
        this.requestedRole = requestedRole;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(
            String status) {
        this.status = status;
    }

    public Boolean getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(
            Boolean isVerified) {
        this.isVerified = isVerified;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(
            LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(
            String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public LocalDate getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(
            LocalDate approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getApprovalRemarks() {
        return approvalRemarks;
    }

    public void setApprovalRemarks(
            String approvalRemarks) {
        this.approvalRemarks = approvalRemarks;
    }

    public Integer getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    public void setFailedLoginAttempts(
            Integer failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }

    public Boolean getAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(
            Boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public LocalDateTime getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(
            LocalDateTime lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(
            String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }
    
    public String getFullName() {
        return fullName;
    }

    public void setFullName(
            String fullName) {
        this.fullName = fullName;
    }
}