package com.finance.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.finance.app.dto.AccountDetailsRequest;
import com.finance.app.dto.CommonResponse;
import com.finance.app.dto.CreateAccountRequest;
import com.finance.app.dto.DeleteAccountRequest;
import com.finance.app.dto.UpdateAccountRequest;
import com.finance.app.service.AccountService;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private AccountService service;

    @PostMapping("/create")
    public CommonResponse createAccount(
            @RequestBody CreateAccountRequest request) {

        return service.createAccount(request);
    }
    
    @PostMapping("/list")
    public CommonResponse getAllAccounts() {

        return service.getAllAccounts();
    }
    
    @PostMapping("/details")
    public CommonResponse getAccountDetails(
            @RequestBody AccountDetailsRequest request) {

        return service.getAccountDetails(request);
    }
    
    @PostMapping("/delete")
    public CommonResponse deleteAccount(
            @RequestBody DeleteAccountRequest request) {

        return service.deleteAccount(request);
    }
    
    @PostMapping("/update")
    public CommonResponse updateAccount(
            @RequestBody UpdateAccountRequest request) {

        return service.updateAccount(request);
    }
}