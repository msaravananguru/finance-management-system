package com.finance.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.finance.app.dto.CommonResponse;
import com.finance.app.dto.budget.BudgetDetailsRequest;
import com.finance.app.dto.budget.BudgetSummaryRequest;
import com.finance.app.dto.budget.CreateBudgetRequest;
import com.finance.app.dto.budget.DeleteBudgetRequest;
import com.finance.app.dto.budget.UpdateBudgetRequest;
import com.finance.app.service.BudgetService;

@RestController
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @PostMapping("/api/budget/create")
    public CommonResponse createBudget(
            @RequestBody CreateBudgetRequest request) {

        return budgetService.createBudget(request);
    }

    @PostMapping("/api/budget/list")
    public CommonResponse getAllBudgets() {

        return budgetService.getAllBudgets();
    }

    @PostMapping("/api/budget/details")
    public CommonResponse getBudgetDetails(
            @RequestBody BudgetDetailsRequest request) {

        return budgetService.getBudgetDetails(request);
    }

    @PostMapping("/api/budget/update")
    public CommonResponse updateBudget(
            @RequestBody UpdateBudgetRequest request) {

        return budgetService.updateBudget(request);
    }

    @PostMapping("/api/budget/delete")
    public CommonResponse deleteBudget(
            @RequestBody DeleteBudgetRequest request) {

        return budgetService.deleteBudget(request);
    }

    @PostMapping("/api/budget/summary")
    public CommonResponse getBudgetSummary(
            @RequestBody BudgetSummaryRequest request) {

        return budgetService.getBudgetSummary(request);
    }
}