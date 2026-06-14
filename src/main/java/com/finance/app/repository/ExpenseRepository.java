package com.finance.app.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finance.app.entity.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

	List<Expense> findByUserIdAndStatus(Long userId, String status);

	List<Expense> findByAccountIdAndStatus(Long accountId, String status);

	List<Expense> findByAccountIdAndStatusAndExpenseDateBetween(Long accountId, String status, LocalDate fromDate,
			LocalDate toDate);

	List<Expense> findByAccountIdAndStatusAndExpenseDateBefore(Long accountId, String status, LocalDate date);
	
	

}