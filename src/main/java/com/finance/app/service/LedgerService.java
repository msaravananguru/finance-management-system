package com.finance.app.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finance.app.dto.CommonResponse;
import com.finance.app.dto.ledger.LedgerEntryResponse;
import com.finance.app.dto.ledger.LedgerRequest;
import com.finance.app.entity.UserAccount;
import com.finance.app.repository.AccountTransferRepository;
import com.finance.app.repository.ExpenseRepository;
import com.finance.app.repository.IncomeRepository;
import com.finance.app.repository.UserAccountRepository;

@Service
public class LedgerService {

	@Autowired
	private UserAccountRepository accountRepository;

	@Autowired
	private IncomeRepository incomeRepository;

	@Autowired
	private ExpenseRepository expenseRepository;

	@Autowired
	private AccountTransferRepository transferRepository;

	public CommonResponse getLedgerStatement(LedgerRequest request) {

		UserAccount account = accountRepository.findById(request.getAccountId()).orElse(null);

		if (account == null) {

			return new CommonResponse(false, "Account Not Found", null);
		}

		if ("DELETED".equals(account.getStatus())) {

			return new CommonResponse(false, "Account Not Found", null);
		}

		if (request.getFromDate().isAfter(request.getToDate())) {

			return new CommonResponse(false, "Invalid Date Range", null);
		}

		BigDecimal openingBalance = calculateOpeningBalance(request.getAccountId(), request.getFromDate());

		List<LedgerEntryResponse> ledger = new ArrayList<>();

		LedgerEntryResponse openingEntry = new LedgerEntryResponse();

		openingEntry.setTransactionDate(request.getFromDate());

		openingEntry.setTransactionType("OPENING_BALANCE");

		openingEntry.setReferenceId(0L);

		openingEntry.setDescription("Opening Balance");

		openingEntry.setCreditAmount(BigDecimal.ZERO);

		openingEntry.setDebitAmount(BigDecimal.ZERO);

		openingEntry.setRunningBalance(openingBalance);

		openingEntry.setSortOrder(0);

		ledger.add(openingEntry);

		addIncomeEntries(request.getAccountId(), request.getFromDate(), request.getToDate(), ledger);

		addExpenseEntries(request.getAccountId(), request.getFromDate(), request.getToDate(), ledger);

		addTransferInEntries(request.getAccountId(), request.getFromDate(), request.getToDate(), ledger);

		addTransferOutEntries(request.getAccountId(), request.getFromDate(), request.getToDate(), ledger);

		ledger.sort((a, b) -> {

			int dateCompare = a.getTransactionDate().compareTo(b.getTransactionDate());

			if (dateCompare != 0) {

				return dateCompare;
			}

			return a.getSortOrder().compareTo(b.getSortOrder());
		});

		BigDecimal runningBalance = openingBalance;

		for (int i = 0; i < ledger.size(); i++) {

			LedgerEntryResponse entry = ledger.get(i);

			if (i == 0) {

				entry.setRunningBalance(runningBalance);

				continue;
			}

			runningBalance = runningBalance.add(entry.getCreditAmount()).subtract(entry.getDebitAmount());

			entry.setRunningBalance(runningBalance);
		}

		return new CommonResponse(true, "Ledger Generated Successfully", ledger);
	}

	private BigDecimal calculateOpeningBalance(Long accountId, java.time.LocalDate fromDate) {

		UserAccount account = accountRepository.findById(accountId).orElse(null);

		if (account == null) {

			return BigDecimal.ZERO;
		}

		BigDecimal balance = account.getOpeningBalance();

		var incomes = incomeRepository.findByAccountIdAndStatusAndIncomeDateBefore(accountId, "ACTIVE", fromDate);

		for (var income : incomes) {

			balance = balance.add(income.getAmount());
		}

		var expenses = expenseRepository.findByAccountIdAndStatusAndExpenseDateBefore(accountId, "ACTIVE", fromDate);

		for (var expense : expenses) {

			balance = balance.subtract(expense.getAmount());
		}

		var transferIns = transferRepository.findByToAccountIdAndStatusAndTransferDateBefore(accountId, "ACTIVE",
				fromDate);

		for (var transfer : transferIns) {

			balance = balance.add(transfer.getAmount());
		}

		var transferOuts = transferRepository.findByFromAccountIdAndStatusAndTransferDateBefore(accountId, "ACTIVE",
				fromDate);

		for (var transfer : transferOuts) {

			balance = balance.subtract(transfer.getAmount());
		}

		return balance;
	}

	private void addIncomeEntries(Long accountId, java.time.LocalDate fromDate, java.time.LocalDate toDate,
			List<LedgerEntryResponse> ledger) {

		var incomes = incomeRepository.findByAccountIdAndStatusAndIncomeDateBetween(accountId, "ACTIVE", fromDate,
				toDate);

		for (var income : incomes) {

			LedgerEntryResponse entry = new LedgerEntryResponse();

			entry.setTransactionDate(income.getIncomeDate());

			entry.setTransactionType("INCOME");

			entry.setReferenceId(income.getId());

			entry.setDescription(income.getIncomeSource());

			entry.setCreditAmount(income.getAmount());

			entry.setDebitAmount(BigDecimal.ZERO);

			entry.setSortOrder(1);

			ledger.add(entry);
		}
	}

	private void addExpenseEntries(Long accountId, java.time.LocalDate fromDate, java.time.LocalDate toDate,
			List<LedgerEntryResponse> ledger) {

		var expenses = expenseRepository.findByAccountIdAndStatusAndExpenseDateBetween(accountId, "ACTIVE", fromDate,
				toDate);

		for (var expense : expenses) {

			LedgerEntryResponse entry = new LedgerEntryResponse();

			entry.setTransactionDate(expense.getExpenseDate());

			entry.setTransactionType("EXPENSE");

			entry.setReferenceId(expense.getId());

			entry.setDescription(expense.getReason());

			entry.setCreditAmount(BigDecimal.ZERO);

			entry.setDebitAmount(expense.getAmount());

			entry.setSortOrder(3);

			ledger.add(entry);
		}
	}

	private void addTransferInEntries(Long accountId, java.time.LocalDate fromDate, java.time.LocalDate toDate,
			List<LedgerEntryResponse> ledger) {

		var transfers = transferRepository.findByToAccountIdAndStatusAndTransferDateBetween(accountId, "ACTIVE",
				fromDate, toDate);

		for (var transfer : transfers) {

			LedgerEntryResponse entry = new LedgerEntryResponse();

			entry.setTransactionDate(transfer.getTransferDate());

			entry.setTransactionType("TRANSFER_IN");

			entry.setReferenceId(transfer.getId());

			entry.setDescription(transfer.getRemarks());

			entry.setCreditAmount(transfer.getAmount());

			entry.setDebitAmount(BigDecimal.ZERO);

			entry.setSortOrder(2);

			ledger.add(entry);
		}
	}

	private void addTransferOutEntries(Long accountId, java.time.LocalDate fromDate, java.time.LocalDate toDate,
			List<LedgerEntryResponse> ledger) {

		var transfers = transferRepository.findByFromAccountIdAndStatusAndTransferDateBetween(accountId, "ACTIVE",
				fromDate, toDate);

		for (var transfer : transfers) {

			LedgerEntryResponse entry = new LedgerEntryResponse();

			entry.setTransactionDate(transfer.getTransferDate());

			entry.setTransactionType("TRANSFER_OUT");

			entry.setReferenceId(transfer.getId());

			entry.setDescription(transfer.getRemarks());

			entry.setCreditAmount(BigDecimal.ZERO);

			entry.setDebitAmount(transfer.getAmount());

			entry.setSortOrder(4);

			ledger.add(entry);
		}
	}
}