package com.finance.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finance.app.dto.CommonResponse;
import com.finance.app.dto.CreateIncomeRequest;
import com.finance.app.dto.DeleteIncomeRequest;
import com.finance.app.dto.IncomeDetailsRequest;
import com.finance.app.dto.UpdateIncomeRequest;
import com.finance.app.service.IncomeService;

@RestController
@RequestMapping("/api/income")
public class IncomeController {

    @Autowired
    private IncomeService service;

    @PostMapping("/create")
    public CommonResponse createIncome(
            @RequestBody CreateIncomeRequest request) {

        return service.createIncome(request);
    }
    
    @PostMapping("/list")
    public CommonResponse getAllIncome() {

        return service.getAllIncome();
    }
    
    @PostMapping("/details")
    public CommonResponse getIncomeDetails(
            @RequestBody IncomeDetailsRequest request) {

        return service.getIncomeDetails(request);
    }
    
    @PostMapping("/update")
    public CommonResponse updateIncome(
            @RequestBody UpdateIncomeRequest request) {

        return service.updateIncome(request);
    }
    
    @PostMapping("/delete")
    public CommonResponse deleteIncome(
            @RequestBody DeleteIncomeRequest request) {

        return service.deleteIncome(request);
    }
}