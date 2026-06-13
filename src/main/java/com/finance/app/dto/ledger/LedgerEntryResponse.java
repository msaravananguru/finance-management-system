package com.finance.app.dto.ledger;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LedgerEntryResponse {

    private LocalDate transactionDate;

    private String transactionType;
    
    private Long referenceId;


    private String description;

    private BigDecimal creditAmount;

    private BigDecimal debitAmount;

    private BigDecimal runningBalance;
    
    private Integer sortOrder;

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    public Long getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(
            LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(
            String transactionType) {
        this.transactionType = transactionType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(
            String description) {
        this.description = description;
    }

    public BigDecimal getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(
            BigDecimal creditAmount) {
        this.creditAmount = creditAmount;
    }

    public BigDecimal getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(
            BigDecimal debitAmount) {
        this.debitAmount = debitAmount;
    }

    public BigDecimal getRunningBalance() {
        return runningBalance;
    }

    public void setRunningBalance(
            BigDecimal runningBalance) {
        this.runningBalance = runningBalance;
    }
}