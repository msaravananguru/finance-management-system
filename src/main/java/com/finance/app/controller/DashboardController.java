package com.finance.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finance.app.dto.CommonResponse;
import com.finance.app.service.DashboardService;

@RestController
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @PostMapping("/api/dashboard")
    public CommonResponse getDashboard() {
        return dashboardService.getDashboard();
    }
}