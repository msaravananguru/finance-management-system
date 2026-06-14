package com.finance.app.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finance.app.dto.CommonResponse;
import com.finance.app.dto.ledger.LedgerRequest;
import com.finance.app.dto.report.AccountReportRequest;
import com.finance.app.dto.report.CategoryReportResponse;
import com.finance.app.dto.report.DateRangeRequest;
import com.finance.app.dto.report.MonthlySummaryRequest;
import com.finance.app.dto.report.MonthlySummaryResponse;
import com.finance.app.entity.Expense;
import com.finance.app.entity.Income;
import com.finance.app.repository.ExpenseRepository;
import com.finance.app.repository.IncomeRepository;
import com.finance.app.repository.UserAccountRepository;

@Service
public class ReportService {

	@Autowired
	private IncomeRepository incomeRepository;

	@Autowired
	private ExpenseRepository expenseRepository;

	@Autowired
	private UserAccountRepository accountRepository;

	@Autowired
	private LedgerService ledgerService;

	public CommonResponse getIncomeReport(DateRangeRequest request) {

		List<Income> incomes = incomeRepository.findByUserIdAndStatus(1L, "ACTIVE");

		List<Income> result = new ArrayList<>();

		for (Income income : incomes) {

			if (income.getIncomeDate() == null) {
				continue;
			}

			if (!income.getIncomeDate().isBefore(request.getFromDate())

					&&

					!income.getIncomeDate().isAfter(request.getToDate())) {

				result.add(income);
			}
		}

		return new CommonResponse(true, "Income Report Generated Successfully", result);
	}

	public CommonResponse getExpenseReport(DateRangeRequest request) {

		List<Expense> expenses = expenseRepository.findByUserIdAndStatus(1L, "ACTIVE");

		List<Expense> result = new ArrayList<>();

		for (Expense expense : expenses) {

			if (expense.getExpenseDate() == null) {
				continue;
			}

			if (!expense.getExpenseDate().isBefore(request.getFromDate())

					&&

					!expense.getExpenseDate().isAfter(request.getToDate())) {

				result.add(expense);
			}
		}

		return new CommonResponse(true, "Expense Report Generated Successfully", result);
	}

	public CommonResponse getCategoryReport(DateRangeRequest request) {

		List<Expense> expenses = expenseRepository.findByUserIdAndStatus(1L, "ACTIVE");

		Map<String, BigDecimal> categoryMap = new HashMap<>();

		for (Expense expense : expenses) {

			if (expense.getExpenseDate() == null) {
				continue;
			}

			if (!expense.getExpenseDate().isBefore(request.getFromDate())

					&&

					!expense.getExpenseDate().isAfter(request.getToDate())) {

				categoryMap.put(expense.getCategory(),

						categoryMap.getOrDefault(expense.getCategory(), BigDecimal.ZERO)

								.add(expense.getAmount()));
			}
		}

		List<CategoryReportResponse> result = new ArrayList<>();

		for (String category : categoryMap.keySet()) {

			CategoryReportResponse response = new CategoryReportResponse();

			response.setCategory(category);

			response.setAmount(categoryMap.get(category));

			result.add(response);
		}

		return new CommonResponse(true, "Category Report Generated Successfully", result);
	}

	public CommonResponse getAccountReport(AccountReportRequest request) {

		LedgerRequest ledgerRequest = new LedgerRequest();

		ledgerRequest.setAccountId(request.getAccountId());

		ledgerRequest.setFromDate(request.getFromDate());

		ledgerRequest.setToDate(request.getToDate());

		return ledgerService.getLedgerStatement(ledgerRequest);
	}

	public CommonResponse getMonthlySummary(MonthlySummaryRequest request) {

		BigDecimal totalIncome = BigDecimal.ZERO;

		List<Income> incomes = incomeRepository.findByUserIdAndStatus(1L, "ACTIVE");

		for (Income income : incomes) {

			if (income.getIncomeDate() == null) {
				continue;
			}

			if (income.getIncomeDate().getYear() == request.getYear()

					&&

					income.getIncomeDate().getMonthValue() == request.getMonth()) {

				totalIncome = totalIncome.add(income.getAmount());
			}
		}

		BigDecimal totalExpense = BigDecimal.ZERO;

		List<Expense> expenses = expenseRepository.findByUserIdAndStatus(1L, "ACTIVE");

		for (Expense expense : expenses) {

			if (expense.getExpenseDate() == null) {
				continue;
			}

			if (expense.getExpenseDate().getYear() == request.getYear()

					&&

					expense.getExpenseDate().getMonthValue() == request.getMonth()) {

				totalExpense = totalExpense.add(expense.getAmount());
			}
		}

		MonthlySummaryResponse response = new MonthlySummaryResponse();

		response.setTotalIncome(totalIncome);

		response.setTotalExpense(totalExpense);

		response.setNetSavings(totalIncome.subtract(totalExpense));

		return new CommonResponse(true, "Monthly Summary Generated Successfully", response);
	}
}