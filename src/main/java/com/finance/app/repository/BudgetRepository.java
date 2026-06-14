package com.finance.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finance.app.entity.Budget;

public interface BudgetRepository
        extends JpaRepository<Budget, Long> {

    List<Budget> findByUserIdAndStatus(
            Long userId,
            String status);

    boolean existsByUserIdAndCategoryAndMonthAndYearAndStatus(
            Long userId,
            String category,
            Integer month,
            Integer year,
            String status);
}