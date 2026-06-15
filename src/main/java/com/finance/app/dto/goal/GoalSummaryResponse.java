package com.finance.app.dto.goal;

import java.math.BigDecimal;

public class GoalSummaryResponse {

    private String goalName;

    private BigDecimal targetAmount;

    private BigDecimal savedAmount;

    private BigDecimal remainingAmount;

    private BigDecimal completionPercentage;

    public String getGoalName() {
        return goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public BigDecimal getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(BigDecimal targetAmount) {
        this.targetAmount = targetAmount;
    }

    public BigDecimal getSavedAmount() {
        return savedAmount;
    }

    public void setSavedAmount(BigDecimal savedAmount) {
        this.savedAmount = savedAmount;
    }

    public BigDecimal getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(BigDecimal remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public BigDecimal getCompletionPercentage() {
        return completionPercentage;
    }

    public void setCompletionPercentage(
            BigDecimal completionPercentage) {
        this.completionPercentage = completionPercentage;
    }
}