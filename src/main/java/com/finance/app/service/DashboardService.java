package com.finance.app.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finance.app.dto.CommonResponse;
import com.finance.app.dto.dashboard.AccountBalanceResponse;
import com.finance.app.dto.dashboard.CategoryExpenseResponse;
import com.finance.app.dto.dashboard.DashboardResponse;
import com.finance.app.entity.Expense;
import com.finance.app.entity.Income;
import com.finance.app.entity.UserAccount;
import com.finance.app.repository.ExpenseRepository;
import com.finance.app.repository.IncomeRepository;
import com.finance.app.repository.UserAccountRepository;

@Service
public class DashboardService {

	@Autowired
	private IncomeRepository incomeRepository;

	@Autowired
	private ExpenseRepository expenseRepository;

	@Autowired
	private UserAccountRepository accountRepository;

	public CommonResponse getDashboard() {

		DashboardResponse response = new DashboardResponse();

		BigDecimal totalIncome = calculateTotalIncome();

		BigDecimal totalExpense = calculateTotalExpense();

		BigDecimal netSavings = totalIncome.subtract(totalExpense);

		BigDecimal totalAccountBalance = calculateTotalAccountBalance();

		response.setTotalIncome(totalIncome);

		response.setTotalExpense(totalExpense);

		response.setNetSavings(netSavings);

		response.setTotalAccountBalance(totalAccountBalance);

		response.setAccountBalances(getAccountBalances());

		response.setCategoryExpenses(getCategoryExpenses());

		return new CommonResponse(true, "Dashboard Loaded Successfully", response);
	}

	private BigDecimal calculateTotalIncome() {

		List<Income> incomes = incomeRepository.findByUserIdAndStatus(1L, "ACTIVE");

		BigDecimal total = BigDecimal.ZERO;

		for (Income income : incomes) {

			total = total.add(income.getAmount());
		}

		return total;
	}

	private BigDecimal calculateTotalExpense() {

		List<Expense> expenses = expenseRepository.findByUserIdAndStatus(1L, "ACTIVE");

		BigDecimal total = BigDecimal.ZERO;

		for (Expense expense : expenses) {

			total = total.add(expense.getAmount());
		}

		return total;
	}

	private BigDecimal calculateTotalAccountBalance() {

		List<UserAccount> accounts = accountRepository.findByUserIdAndStatus(1L, "ACTIVE");

		BigDecimal total = BigDecimal.ZERO;

		for (UserAccount account : accounts) {

			total = total.add(account.getCurrentBalance());
		}

		return total;
	}

	private List<AccountBalanceResponse> getAccountBalances() {

		List<AccountBalanceResponse> result = new ArrayList<>();

		List<UserAccount> accounts = accountRepository.findByUserIdAndStatus(1L, "ACTIVE");

		for (UserAccount account : accounts) {

			AccountBalanceResponse response = new AccountBalanceResponse();

			response.setAccountName(account.getAccountName());

			response.setBalance(account.getCurrentBalance());

			result.add(response);
		}

		return result;
	}

	private List<CategoryExpenseResponse> getCategoryExpenses() {

		List<CategoryExpenseResponse> result = new ArrayList<>();

		List<Expense> expenses = expenseRepository.findByUserIdAndStatus(1L, "ACTIVE");

		java.util.Map<String, BigDecimal> map = new java.util.HashMap<>();

		for (Expense expense : expenses) {

			map.put(expense.getCategory(),

					map.getOrDefault(expense.getCategory(), BigDecimal.ZERO)

							.add(expense.getAmount()));
		}

		for (String category : map.keySet()) {

			CategoryExpenseResponse response = new CategoryExpenseResponse();

			response.setCategory(category);

			response.setAmount(map.get(category));

			result.add(response);
		}

		return result;
	}
}