package com.finance.app.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finance.app.entity.AccountTransfer;

public interface AccountTransferRepository extends JpaRepository<AccountTransfer, Long> {

	List<AccountTransfer> findByUserIdAndStatus(Long userId, String status);

	List<AccountTransfer> findByFromAccountIdAndStatus(Long fromAccountId, String status);

	List<AccountTransfer> findByToAccountIdAndStatus(Long toAccountId, String status);

	List<AccountTransfer> findByFromAccountIdAndStatusAndTransferDateBetween(Long fromAccountId, String status,
			LocalDate fromDate, LocalDate toDate);

	List<AccountTransfer> findByToAccountIdAndStatusAndTransferDateBetween(Long toAccountId, String status,
			LocalDate fromDate, LocalDate toDate);

	List<AccountTransfer> findByFromAccountIdAndStatusAndTransferDateBefore(Long fromAccountId, String status,
			LocalDate date);

	List<AccountTransfer> findByToAccountIdAndStatusAndTransferDateBefore(Long toAccountId, String status,
			LocalDate date);
}