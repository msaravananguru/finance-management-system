package com.finance.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finance.app.entity.PendingRegistration;

public interface PendingRegistrationRepository
        extends JpaRepository<PendingRegistration, Long> {

    Optional<PendingRegistration> findByEmail(
            String email);

    boolean existsByUsername(
            String username);

    boolean existsByEmail(
            String email);
}