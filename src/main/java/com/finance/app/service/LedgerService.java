package com.finance.app.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finance.app.dto.CommonResponse;
import com.finance.app.dto.ledger.LedgerEntryResponse;
import com.finance.app.dto.ledger.LedgerRequest;
import com.finance.app.entity.UserAccount;
import com.finance.app.repository.AccountTransferRepository;
import com.finance.app.repository.ExpenseRepository;
import com.finance.app.repository.IncomeRepository;
import com.finance.app.repository.UserAccountRepository;

@Service
public class LedgerService {

    @Autowired
    private UserAccountRepository accountRepository;

    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private AccountTransferRepository transferRepository;

    public CommonResponse getLedgerStatement(
            LedgerRequest request) {

        UserAccount account =
                accountRepository.findById(
                        request.getAccountId())
                        .orElse(null);

        if(account == null) {

            return new CommonResponse(
                    false,
                    "Account Not Found",
                    null);
        }

        if("DELETED".equals(
                account.getStatus())) {

            return new CommonResponse(
                    false,
                    "Account Not Found",
                    null);
        }

        if(request.getFromDate()
                .isAfter(request.getToDate())) {

            return new CommonResponse(
                    false,
                    "Invalid Date Range",
                    null);
        }

        List<LedgerEntryResponse> ledger =
                new ArrayList<>();

        return new CommonResponse(
                true,
                "Ledger Generated Successfully",
                ledger);
    }
}