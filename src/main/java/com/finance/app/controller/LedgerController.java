package com.finance.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.finance.app.dto.CommonResponse;
import com.finance.app.dto.ledger.LedgerRequest;
import com.finance.app.service.LedgerService;

@RestController
@RequestMapping("/api/ledger")
public class LedgerController {

    @Autowired
    private LedgerService service;

    @PostMapping("/statement")
    public CommonResponse getLedgerStatement(
            @RequestBody LedgerRequest request) {

        return service.getLedgerStatement(
                request);
    }
}