package com.finance.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finance.app.entity.Expense;

public interface ExpenseRepository
        extends JpaRepository<Expense, Long> {

    List<Expense> findByUserIdAndStatus(
            Long userId,
            String status);
}