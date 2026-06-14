package com.finance.app.dto.dashboard;

import java.math.BigDecimal;

public class AccountBalanceResponse {

    private String accountName;

    private BigDecimal balance;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}