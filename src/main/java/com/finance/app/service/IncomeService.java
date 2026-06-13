package com.finance.app.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finance.app.dto.CommonResponse;
import com.finance.app.dto.CreateIncomeRequest;
import com.finance.app.dto.DeleteIncomeRequest;
import com.finance.app.dto.IncomeDetailsRequest;
import com.finance.app.dto.UpdateIncomeRequest;
import com.finance.app.entity.Income;
import com.finance.app.entity.UserAccount;
import com.finance.app.repository.IncomeRepository;
import com.finance.app.repository.UserAccountRepository;

@Service
public class IncomeService {

	@Autowired
	private IncomeRepository incomeRepository;

	@Autowired
	private UserAccountRepository accountRepository;

	public CommonResponse createIncome(CreateIncomeRequest request) {

		UserAccount account = accountRepository.findById(request.getAccountId()).orElse(null);

		if (account == null) {

			return new CommonResponse(false, "Account Not Found", null);
		}

		Income income = new Income();

		income.setUserId(1L);

		income.setAccountId(request.getAccountId());

		income.setAmount(request.getAmount());

		income.setIncomeSource(request.getIncomeSource());

		income.setDescription(request.getDescription());

		income.setIncomeDate(request.getIncomeDate());

		income.setStatus("ACTIVE");

		incomeRepository.save(income);

		BigDecimal newBalance = account.getCurrentBalance().add(request.getAmount());

		account.setCurrentBalance(newBalance);

		accountRepository.save(account);

		return new CommonResponse(true, "Income Added Successfully", income);
	}

	public CommonResponse getAllIncome() {

		return new CommonResponse(true, "Income List Fetched Successfully",
				incomeRepository.findByUserIdAndStatus(1L, "ACTIVE"));
	}

	public CommonResponse getIncomeDetails(IncomeDetailsRequest request) {

		Income income = incomeRepository.findById(request.getIncomeId()).orElse(null);

		if (income == null) {

			return new CommonResponse(false, "Income Not Found", null);
		}

		return new CommonResponse(true, "Income Found", income);
	}

	public CommonResponse updateIncome(UpdateIncomeRequest request) {

		Income income = incomeRepository.findById(request.getIncomeId()).orElse(null);

		if (income == null) {

			return new CommonResponse(false, "Income Not Found", null);
		}
		if("DELETED".equals(income.getStatus())) {

		    return new CommonResponse(
		            false,
		            "Cannot Update Deleted Income",
		            null);
		}
		
		if(request.getAmount() == null
		        || request.getAmount().doubleValue() <= 0) {

		    return new CommonResponse(
		            false,
		            "Amount Must Be Greater Than Zero",
		            null);
		}

		UserAccount account = accountRepository.findById(income.getAccountId()).orElse(null);

		if (account == null) {

			return new CommonResponse(false, "Account Not Found", null);
		}

		account.setCurrentBalance(account.getCurrentBalance().subtract(income.getAmount()));

		account.setCurrentBalance(account.getCurrentBalance().add(request.getAmount()));

		income.setAmount(request.getAmount());

		income.setIncomeSource(request.getIncomeSource());

		income.setDescription(request.getDescription());

		income.setIncomeDate(request.getIncomeDate());

		incomeRepository.save(income);

		accountRepository.save(account);

		return new CommonResponse(true, "Income Updated Successfully", income);
	}

	public CommonResponse deleteIncome(DeleteIncomeRequest request) {

		Income income = incomeRepository.findById(request.getIncomeId()).orElse(null);

		if (income == null) {

			return new CommonResponse(false, "Income Not Found", null);
		}
		if("DELETED".equals(income.getStatus())) {

		    return new CommonResponse(
		            false,
		            "Income Already Deleted",
		            null);
		}

		UserAccount account = accountRepository.findById(income.getAccountId()).orElse(null);

		if (account == null) {

			return new CommonResponse(false, "Account Not Found", null);
		}

		account.setCurrentBalance(account.getCurrentBalance().subtract(income.getAmount()));

		accountRepository.save(account);

		income.setStatus("DELETED");

		incomeRepository.save(income);

		return new CommonResponse(true, "Income Deleted Successfully", null);
	}
}