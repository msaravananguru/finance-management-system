package com.finance.app.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finance.app.entity.Income;

public interface IncomeRepository extends JpaRepository<Income, Long> {

    List<Income> findByUserIdAndStatus(
            Long userId,
            String status);

    List<Income> findByAccountIdAndStatus(
            Long accountId,
            String status);
    
    List<Income> findByAccountIdAndStatusAndIncomeDateBetween(
            Long accountId,
            String status,
            LocalDate fromDate,
            LocalDate toDate);
    
    List<Income> findByAccountIdAndStatusAndIncomeDateBefore(
            Long accountId,
            String status,
            LocalDate date);
}