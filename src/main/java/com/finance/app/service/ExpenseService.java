package com.finance.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finance.app.dto.CommonResponse;
import com.finance.app.dto.CreateExpenseRequest;
import com.finance.app.dto.DeleteExpenseRequest;
import com.finance.app.dto.ExpenseDetailsRequest;
import com.finance.app.dto.UpdateExpenseRequest;
import com.finance.app.entity.CategoryMapping;
import com.finance.app.entity.Expense;
import com.finance.app.entity.UserAccount;
import com.finance.app.repository.CategoryMappingRepository;
import com.finance.app.repository.ExpenseRepository;
import com.finance.app.repository.UserAccountRepository;

@Service
public class ExpenseService {

	@Autowired
	private ExpenseRepository expenseRepository;

	@Autowired
	private UserAccountRepository accountRepository;

	@Autowired
	private CategoryMappingRepository categoryRepository;

	public CommonResponse createExpense(CreateExpenseRequest request) {

		UserAccount account = accountRepository.findById(request.getAccountId()).orElse(null);

		if (account == null) {

			return new CommonResponse(false, "Account Not Found", null);
		}

		if ("DELETED".equals(account.getStatus())) {

			return new CommonResponse(false, "Account Not Found", null);
		}

		if (request.getAmount() == null || request.getAmount().doubleValue() <= 0) {

			return new CommonResponse(false, "Amount Must Be Greater Than Zero", null);
		}

		String category = "OTHER";

		List<CategoryMapping> categories = categoryRepository.findByUserIdAndStatus(1L, "ACTIVE");

		String reason = request.getReason().toLowerCase();

		for (CategoryMapping mapping : categories) {

			if (reason.contains(mapping.getKeyword().toLowerCase())) {

				category = mapping.getCategory();

				break;
			}
		}

		Expense expense = new Expense();

		expense.setUserId(1L);
		expense.setAccountId(request.getAccountId());

		expense.setAmount(request.getAmount());

		expense.setReason(request.getReason());

		expense.setCategory(category);

		expense.setDescription(request.getDescription());

		expense.setExpenseDate(request.getExpenseDate());

		expense.setStatus("ACTIVE");

		expenseRepository.save(expense);

		account.setCurrentBalance(account.getCurrentBalance().subtract(request.getAmount()));

		accountRepository.save(account);

		return new CommonResponse(true, "Expense Added Successfully", expense);
	}

	public CommonResponse getAllExpenses() {

		return new CommonResponse(true, "Expense List Fetched Successfully",
				expenseRepository.findByUserIdAndStatus(1L, "ACTIVE"));
	}

	public CommonResponse getExpenseDetails(ExpenseDetailsRequest request) {

		Expense expense = expenseRepository.findById(request.getExpenseId()).orElse(null);

		if (expense == null) {

			return new CommonResponse(false, "Expense Not Found", null);
		}

		if ("DELETED".equals(expense.getStatus())) {

			return new CommonResponse(false, "Expense Not Found", null);
		}

		return new CommonResponse(true, "Expense Found", expense);
	}

	public CommonResponse updateExpense(UpdateExpenseRequest request) {

		Expense expense = expenseRepository.findById(request.getExpenseId()).orElse(null);

		if (expense == null) {

			return new CommonResponse(false, "Expense Not Found", null);
		}

		if ("DELETED".equals(expense.getStatus())) {

			return new CommonResponse(false, "Cannot Update Deleted Expense", null);
		}

		UserAccount account = accountRepository.findById(expense.getAccountId()).orElse(null);

		if (account == null) {

			return new CommonResponse(false, "Account Not Found", null);
		}

		account.setCurrentBalance(account.getCurrentBalance().add(expense.getAmount()));

		account.setCurrentBalance(account.getCurrentBalance().subtract(request.getAmount()));

		String category = "OTHER";

		List<CategoryMapping> categories = categoryRepository.findByUserIdAndStatus(1L, "ACTIVE");

		String reason = request.getReason().toLowerCase();

		for (CategoryMapping mapping : categories) {

			if (reason.contains(mapping.getKeyword().toLowerCase())) {

				category = mapping.getCategory();

				break;
			}
		}

		expense.setAccountId(request.getAccountId());

		expense.setAmount(request.getAmount());

		expense.setReason(request.getReason());

		expense.setCategory(category);

		expense.setDescription(request.getDescription());

		expense.setExpenseDate(request.getExpenseDate());

		expenseRepository.save(expense);

		accountRepository.save(account);

		return new CommonResponse(true, "Expense Updated Successfully", expense);
	}

	public CommonResponse deleteExpense(DeleteExpenseRequest request) {

		Expense expense = expenseRepository.findById(request.getExpenseId()).orElse(null);

		if (expense == null) {

			return new CommonResponse(false, "Expense Not Found", null);
		}

		if ("DELETED".equals(expense.getStatus())) {

			return new CommonResponse(false, "Expense Already Deleted", null);
		}

		UserAccount account = accountRepository.findById(expense.getAccountId()).orElse(null);

		if (account == null) {

			return new CommonResponse(false, "Account Not Found", null);
		}

		account.setCurrentBalance(account.getCurrentBalance().add(expense.getAmount()));

		accountRepository.save(account);

		expense.setStatus("DELETED");

		expenseRepository.save(expense);

		return new CommonResponse(true, "Expense Deleted Successfully", null);
	}
}