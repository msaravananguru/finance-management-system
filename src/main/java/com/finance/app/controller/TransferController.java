package com.finance.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.finance.app.dto.CommonResponse;
import com.finance.app.dto.transfer.CreateTransferRequest;
import com.finance.app.dto.transfer.DeleteTransferRequest;
import com.finance.app.dto.transfer.TransferDetailsRequest;
import com.finance.app.dto.transfer.UpdateTransferRequest;
import com.finance.app.service.TransferService;

@RestController
@RequestMapping("/api/transfer")
public class TransferController {

    @Autowired
    private TransferService service;

    @PostMapping("/create")
    public CommonResponse createTransfer(
            @RequestBody CreateTransferRequest request) {

        return service.createTransfer(request);
    }

    @PostMapping("/list")
    public CommonResponse getAllTransfers() {

        return service.getAllTransfers();
    }

    @PostMapping("/details")
    public CommonResponse getTransferDetails(
            @RequestBody TransferDetailsRequest request) {

        return service.getTransferDetails(request);
    }
    
    @PostMapping("/update")
    public CommonResponse updateTransfer(
            @RequestBody UpdateTransferRequest request) {

        return service.updateTransfer(request);
    }

    @PostMapping("/delete")
    public CommonResponse deleteTransfer(
            @RequestBody DeleteTransferRequest request) {

        return service.deleteTransfer(request);
    }
}