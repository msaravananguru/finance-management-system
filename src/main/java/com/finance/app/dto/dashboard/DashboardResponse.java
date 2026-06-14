package com.finance.app.dto.dashboard;

import java.math.BigDecimal;
import java.util.List;

public class DashboardResponse {

    private BigDecimal totalIncome;

    private BigDecimal totalExpense;

    private BigDecimal netSavings;

    private BigDecimal totalAccountBalance;

    private List<AccountBalanceResponse> accountBalances;

    private List<CategoryExpenseResponse> categoryExpenses;

    public BigDecimal getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(BigDecimal totalIncome) {
        this.totalIncome = totalIncome;
    }

    public BigDecimal getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(BigDecimal totalExpense) {
        this.totalExpense = totalExpense;
    }

    public BigDecimal getNetSavings() {
        return netSavings;
    }

    public void setNetSavings(BigDecimal netSavings) {
        this.netSavings = netSavings;
    }

    public BigDecimal getTotalAccountBalance() {
        return totalAccountBalance;
    }

    public void setTotalAccountBalance(BigDecimal totalAccountBalance) {
        this.totalAccountBalance = totalAccountBalance;
    }

    public List<AccountBalanceResponse> getAccountBalances() {
        return accountBalances;
    }

    public void setAccountBalances(
            List<AccountBalanceResponse> accountBalances) {
        this.accountBalances = accountBalances;
    }

    public List<CategoryExpenseResponse> getCategoryExpenses() {
        return categoryExpenses;
    }

    public void setCategoryExpenses(
            List<CategoryExpenseResponse> categoryExpenses) {
        this.categoryExpenses = categoryExpenses;
    }
}