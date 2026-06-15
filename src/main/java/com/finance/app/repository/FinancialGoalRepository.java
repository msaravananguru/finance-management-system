package com.finance.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finance.app.entity.FinancialGoal;

public interface FinancialGoalRepository
        extends JpaRepository<FinancialGoal, Long> {

    List<FinancialGoal> findByUserIdAndStatus(
            Long userId,
            String status);

    boolean existsByUserIdAndGoalNameAndStatus(
            Long userId,
            String goalName,
            String status);
}