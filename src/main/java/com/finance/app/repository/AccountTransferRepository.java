package com.finance.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finance.app.entity.AccountTransfer;

public interface AccountTransferRepository extends JpaRepository<AccountTransfer, Long> {

	List<AccountTransfer> findByUserIdAndStatus(Long userId, String status);
}