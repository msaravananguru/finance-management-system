package com.finance.app.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finance.app.dto.CommonResponse;
import com.finance.app.dto.budget.BudgetDetailsRequest;
import com.finance.app.dto.budget.BudgetSummaryRequest;
import com.finance.app.dto.budget.BudgetSummaryResponse;
import com.finance.app.dto.budget.CreateBudgetRequest;
import com.finance.app.dto.budget.DeleteBudgetRequest;
import com.finance.app.dto.budget.UpdateBudgetRequest;
import com.finance.app.entity.Budget;
import com.finance.app.entity.Expense;
import com.finance.app.repository.BudgetRepository;
import com.finance.app.repository.ExpenseRepository;

@Service
public class BudgetService {

	@Autowired
	private BudgetRepository budgetRepository;

	@Autowired
	private ExpenseRepository expenseRepository;

	public CommonResponse createBudget(CreateBudgetRequest request) {

		if (request.getBudgetAmount() == null || request.getBudgetAmount().doubleValue() <= 0) {

			return new CommonResponse(false, "Budget Amount Must Be Greater Than Zero", null);
		}

		boolean exists = budgetRepository.existsByUserIdAndCategoryAndMonthAndYearAndStatus(1L,
				request.getCategory().toUpperCase(), request.getMonth(), request.getYear(), "ACTIVE");

		if (exists) {

			return new CommonResponse(false, "Budget Already Exists", null);
		}

		Budget budget = new Budget();

		budget.setUserId(1L);

		budget.setCategory(request.getCategory().toUpperCase());

		budget.setBudgetAmount(request.getBudgetAmount());

		budget.setMonth(request.getMonth());

		budget.setYear(request.getYear());

		budget.setStatus("ACTIVE");

		budgetRepository.save(budget);

		return new CommonResponse(true, "Budget Created Successfully", budget);
	}

	public CommonResponse getAllBudgets() {

		return new CommonResponse(true, "Budget List Fetched Successfully",
				budgetRepository.findByUserIdAndStatus(1L, "ACTIVE"));
	}

	public CommonResponse getBudgetDetails(BudgetDetailsRequest request) {

		Budget budget = budgetRepository.findById(request.getBudgetId()).orElse(null);

		if (budget == null) {

			return new CommonResponse(false, "Budget Not Found", null);
		}

		return new CommonResponse(true, "Budget Found", budget);
	}

	public CommonResponse updateBudget(UpdateBudgetRequest request) {

		Budget budget = budgetRepository.findById(request.getBudgetId()).orElse(null);

		if (budget == null) {

			return new CommonResponse(false, "Budget Not Found", null);
		}

		if ("DELETED".equals(budget.getStatus())) {

			return new CommonResponse(false, "Cannot Update Deleted Budget", null);
		}

		budget.setCategory(request.getCategory().toUpperCase());

		budget.setBudgetAmount(request.getBudgetAmount());

		budget.setMonth(request.getMonth());

		budget.setYear(request.getYear());

		budgetRepository.save(budget);

		return new CommonResponse(true, "Budget Updated Successfully", budget);
	}

	public CommonResponse deleteBudget(DeleteBudgetRequest request) {

		Budget budget = budgetRepository.findById(request.getBudgetId()).orElse(null);

		if (budget == null) {

			return new CommonResponse(false, "Budget Not Found", null);
		}

		if ("DELETED".equals(budget.getStatus())) {

			return new CommonResponse(false, "Budget Already Deleted", null);
		}

		budget.setStatus("DELETED");

		budgetRepository.save(budget);

		return new CommonResponse(true, "Budget Deleted Successfully", null);
	}

	public CommonResponse getBudgetSummary(BudgetSummaryRequest request) {

		List<Budget> budgets = budgetRepository.findByUserIdAndStatus(1L, "ACTIVE");

		List<Expense> expenses = expenseRepository.findByUserIdAndStatus(1L, "ACTIVE");

		List<BudgetSummaryResponse> result = new ArrayList<>();

		for (Budget budget : budgets) {

			if (!budget.getMonth().equals(request.getMonth()) || !budget.getYear().equals(request.getYear())) {

				continue;
			}

			BigDecimal spentAmount = BigDecimal.ZERO;

			for (Expense expense : expenses) {

				if (expense.getExpenseDate() == null) {
					continue;
				}

				if (budget.getCategory().equals(expense.getCategory())

						&&

						expense.getExpenseDate().getMonthValue() == request.getMonth()

						&&

						expense.getExpenseDate().getYear() == request.getYear()) {

					spentAmount = spentAmount.add(expense.getAmount());
				}
			}

			BudgetSummaryResponse response = new BudgetSummaryResponse();

			response.setCategory(budget.getCategory());

			response.setBudgetAmount(budget.getBudgetAmount());

			response.setSpentAmount(spentAmount);

			response.setRemainingAmount(budget.getBudgetAmount().subtract(spentAmount));

			BigDecimal utilization = BigDecimal.ZERO;

			if (budget.getBudgetAmount().compareTo(BigDecimal.ZERO) > 0) {

				utilization = spentAmount.multiply(BigDecimal.valueOf(100)).divide(budget.getBudgetAmount(), 2,
						java.math.RoundingMode.HALF_UP);
			}

			response.setUtilizationPercentage(utilization);

			result.add(response);
		}

		return new CommonResponse(true, "Budget Summary Generated Successfully", result);
	}
}