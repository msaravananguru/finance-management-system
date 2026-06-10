package com.finance.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.finance.app.entity.UserAccount;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

	boolean existsByUserIdAndAccountName(
	        Long userId,
	        String accountName);
	List<UserAccount> findByUserIdAndIsDefault(
            Long userId,
            Boolean isDefault);
	
	List<UserAccount> findByUserIdAndStatus(
	        Long userId,
	        String status);
}