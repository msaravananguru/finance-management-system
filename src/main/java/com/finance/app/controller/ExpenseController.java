package com.finance.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.finance.app.dto.CommonResponse;
import com.finance.app.dto.CreateExpenseRequest;
import com.finance.app.dto.DeleteExpenseRequest;
import com.finance.app.dto.ExpenseDetailsRequest;
import com.finance.app.dto.UpdateExpenseRequest;
import com.finance.app.service.ExpenseService;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {

	@Autowired
	private ExpenseService service;

	@PostMapping("/create")
	public CommonResponse createExpense(@RequestBody CreateExpenseRequest request) {

		return service.createExpense(request);
	}

	@PostMapping("/list")
	public CommonResponse getAllExpenses() {

		return service.getAllExpenses();
	}

	@PostMapping("/details")
	public CommonResponse getExpenseDetails(@RequestBody ExpenseDetailsRequest request) {

		return service.getExpenseDetails(request);
	}
	
	@PostMapping("/update")
	public CommonResponse updateExpense(
	        @RequestBody UpdateExpenseRequest request) {

	    return service.updateExpense(request);
	}

	@PostMapping("/delete")
	public CommonResponse deleteExpense(
	        @RequestBody DeleteExpenseRequest request) {

	    return service.deleteExpense(request);
	}
}