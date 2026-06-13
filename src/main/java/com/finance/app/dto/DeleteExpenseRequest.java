package com.finance.app.dto;

public class DeleteExpenseRequest {

    private Long expenseId;

    public Long getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(Long expenseId) {
        this.expenseId = expenseId;
    }
}