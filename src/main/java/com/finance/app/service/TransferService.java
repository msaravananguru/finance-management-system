package com.finance.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finance.app.dto.CommonResponse;
import com.finance.app.dto.transfer.CreateTransferRequest;
import com.finance.app.dto.transfer.DeleteTransferRequest;
import com.finance.app.dto.transfer.TransferDetailsRequest;
import com.finance.app.dto.transfer.UpdateTransferRequest;
import com.finance.app.entity.AccountTransfer;
import com.finance.app.entity.UserAccount;
import com.finance.app.repository.AccountTransferRepository;
import com.finance.app.repository.UserAccountRepository;

@Service
public class TransferService {

	@Autowired
	private AccountTransferRepository transferRepository;

	@Autowired
	private UserAccountRepository accountRepository;

	public CommonResponse createTransfer(CreateTransferRequest request) {

		if (request.getFromAccountId().equals(request.getToAccountId())) {

			return new CommonResponse(false, "Source And Destination Account Cannot Be Same", null);
		}

		if (request.getAmount() == null || request.getAmount().doubleValue() <= 0) {

			return new CommonResponse(false, "Amount Must Be Greater Than Zero", null);
		}

		UserAccount fromAccount = accountRepository.findById(request.getFromAccountId()).orElse(null);

		UserAccount toAccount = accountRepository.findById(request.getToAccountId()).orElse(null);

		if (fromAccount == null || toAccount == null) {

			return new CommonResponse(false, "Account Not Found", null);
		}

		if ("DELETED".equals(fromAccount.getStatus()) || "DELETED".equals(toAccount.getStatus())) {

			return new CommonResponse(false, "Account Not Found", null);
		}

		AccountTransfer transfer = new AccountTransfer();

		transfer.setUserId(1L);

		transfer.setFromAccountId(request.getFromAccountId());

		transfer.setToAccountId(request.getToAccountId());

		transfer.setAmount(request.getAmount());

		transfer.setRemarks(request.getRemarks());

		transfer.setTransferDate(request.getTransferDate());

		transfer.setStatus("ACTIVE");

		transferRepository.save(transfer);

		fromAccount.setCurrentBalance(fromAccount.getCurrentBalance().subtract(request.getAmount()));

		toAccount.setCurrentBalance(toAccount.getCurrentBalance().add(request.getAmount()));

		accountRepository.save(fromAccount);

		accountRepository.save(toAccount);

		return new CommonResponse(true, "Transfer Completed Successfully", transfer);
	}

	public CommonResponse getAllTransfers() {

		return new CommonResponse(true, "Transfer List Fetched Successfully",
				transferRepository.findByUserIdAndStatus(1L, "ACTIVE"));
	}

	public CommonResponse getTransferDetails(TransferDetailsRequest request) {

		AccountTransfer transfer = transferRepository.findById(request.getTransferId()).orElse(null);

		if (transfer == null) {

			return new CommonResponse(false, "Transfer Not Found", null);
		}

		if ("DELETED".equals(transfer.getStatus())) {

			return new CommonResponse(false, "Transfer Not Found", null);
		}

		return new CommonResponse(true, "Transfer Found", transfer);
	}

	public CommonResponse updateTransfer(UpdateTransferRequest request) {

		AccountTransfer transfer = transferRepository.findById(request.getTransferId()).orElse(null);

		if (transfer == null) {

			return new CommonResponse(false, "Transfer Not Found", null);
		}

		if ("DELETED".equals(transfer.getStatus())) {

			return new CommonResponse(false, "Cannot Update Deleted Transfer", null);
		}

		if (request.getAmount() == null || request.getAmount().doubleValue() <= 0) {

			return new CommonResponse(false, "Amount Must Be Greater Than Zero", null);
		}

		UserAccount oldFromAccount = accountRepository.findById(transfer.getFromAccountId()).orElse(null);

		UserAccount oldToAccount = accountRepository.findById(transfer.getToAccountId()).orElse(null);

		if (oldFromAccount == null || oldToAccount == null) {

			return new CommonResponse(false, "Account Not Found", null);
		}

		oldFromAccount.setCurrentBalance(oldFromAccount.getCurrentBalance().add(transfer.getAmount()));

		oldToAccount.setCurrentBalance(oldToAccount.getCurrentBalance().subtract(transfer.getAmount()));
		
		oldFromAccount.setCurrentBalance(oldFromAccount.getCurrentBalance().subtract(request.getAmount()));

		oldToAccount.setCurrentBalance(oldToAccount.getCurrentBalance().add(request.getAmount()));

		transfer.setAmount(request.getAmount());

		transfer.setRemarks(request.getRemarks());

		transfer.setTransferDate(request.getTransferDate());

		transferRepository.save(transfer);

		accountRepository.save(oldFromAccount);
		accountRepository.save(oldToAccount);


		return new CommonResponse(true, "Transfer Updated Successfully", transfer);
	}

	public CommonResponse deleteTransfer(DeleteTransferRequest request) {

		AccountTransfer transfer = transferRepository.findById(request.getTransferId()).orElse(null);

		if (transfer == null) {

			return new CommonResponse(false, "Transfer Not Found", null);
		}

		if ("DELETED".equals(transfer.getStatus())) {

			return new CommonResponse(false, "Transfer Already Deleted", null);
		}

		UserAccount fromAccount = accountRepository.findById(transfer.getFromAccountId()).orElse(null);

		UserAccount toAccount = accountRepository.findById(transfer.getToAccountId()).orElse(null);

		if (fromAccount == null || toAccount == null) {

			return new CommonResponse(false, "Account Not Found", null);
		}

		fromAccount.setCurrentBalance(fromAccount.getCurrentBalance().add(transfer.getAmount()));

		toAccount.setCurrentBalance(toAccount.getCurrentBalance().subtract(transfer.getAmount()));

		accountRepository.save(fromAccount);
		accountRepository.save(toAccount);

		transfer.setStatus("DELETED");

		transferRepository.save(transfer);

		return new CommonResponse(true, "Transfer Deleted Successfully", null);
	}
}