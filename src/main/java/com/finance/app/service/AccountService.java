package com.finance.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finance.app.dto.AccountDetailsRequest;
import com.finance.app.dto.CommonResponse;
import com.finance.app.dto.CreateAccountRequest;
import com.finance.app.dto.DeleteAccountRequest;
import com.finance.app.dto.UpdateAccountRequest;
import com.finance.app.entity.UserAccount;
import com.finance.app.repository.UserAccountRepository;

@Service
public class AccountService {

    @Autowired
    private UserAccountRepository repository;

    public CommonResponse createAccount(CreateAccountRequest request) {

        UserAccount account = new UserAccount();

        account.setUserId(1L);
        account.setAccountName(request.getAccountName());
        account.setAccountType(request.getAccountType());
        account.setOpeningBalance(request.getOpeningBalance());
        account.setCurrentBalance(request.getOpeningBalance());
        account.setIsDefault(request.getIsDefault());
        account.setStatus("ACTIVE");

        boolean exists =
        		repository.existsByUserIdAndAccountName(
        		        1L,
        		        request.getAccountName());
        
        if(exists) {

            return new CommonResponse(
                    false,
                    "Account Already Exists",
                    null);
        }
        
        if(Boolean.TRUE.equals(
                request.getIsDefault())) {

        	List<UserAccount> defaults =
        	        repository.findByUserIdAndIsDefault(
        	                1L,
        	                true);

        	for (UserAccount existingAccount : defaults) {

        	    existingAccount.setIsDefault(false);

        	    repository.save(existingAccount);
        	}
        }
        
        repository.save(account);

        return new CommonResponse(
                true,
                "Account Created Successfully",
                account
        );
    }
    public CommonResponse getAllAccounts() {

        return new CommonResponse(
                true,
                "Accounts Fetched Successfully",
                repository.findByUserIdAndStatus(
                        1L,
                        "ACTIVE")
        );
    }
    
    public CommonResponse getAccountDetails(
            AccountDetailsRequest request) {

        UserAccount account =
                repository.findById(request.getAccountId())
                .orElse(null);

        return new CommonResponse(
                true,
                "Account Found",
                account
        );
    }
    
    public CommonResponse deleteAccount(
            DeleteAccountRequest request) {

        UserAccount account =
                repository.findById(
                        request.getAccountId())
                        .orElse(null);

        if(account == null) {

            return new CommonResponse(
                    false,
                    "Account Not Found",
                    null);
        }

        account.setStatus("DELETED");

        repository.save(account);

        return new CommonResponse(
                true,
                "Account Deleted Successfully",
                null);
    }
    public CommonResponse updateAccount(
            UpdateAccountRequest request) {

        UserAccount account =
                repository.findById(
                        request.getAccountId())
                        .orElse(null);

        if(account == null) {

            return new CommonResponse(
                    false,
                    "Account Not Found",
                    null);
        }

        account.setAccountName(
                request.getAccountName());
        
        if(Boolean.TRUE.equals(
                request.getIsDefault())) {

            List<UserAccount> defaults =
                    repository.findByUserIdAndIsDefault(
                            1L,
                            true);

            for(UserAccount existingAccount : defaults) {

                existingAccount.setIsDefault(false);

                repository.save(existingAccount);
            }
        }

        account.setIsDefault(
                request.getIsDefault());

        repository.save(account);

        return new CommonResponse(
                true,
                "Account Updated Successfully",
                account);
    }
}