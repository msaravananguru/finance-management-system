package com.finance.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.finance.app.dto.CommonResponse;
import com.finance.app.dto.report.AccountReportRequest;
import com.finance.app.dto.report.DateRangeRequest;
import com.finance.app.dto.report.MonthlySummaryRequest;
import com.finance.app.service.ReportService;

@RestController
public class ReportController {

	@Autowired
	private ReportService reportService;

	@PostMapping("/api/report/income")
	public CommonResponse getIncomeReport(@RequestBody DateRangeRequest request) {

		return reportService.getIncomeReport(request);
	}

	@PostMapping("/api/report/expense")
	public CommonResponse getExpenseReport(@RequestBody DateRangeRequest request) {

		return reportService.getExpenseReport(request);
	}

	@PostMapping("/api/report/category")
	public CommonResponse getCategoryReport(@RequestBody DateRangeRequest request) {

		return reportService.getCategoryReport(request);
	}

	@PostMapping("/api/report/account")
	public CommonResponse getAccountReport(@RequestBody AccountReportRequest request) {

		return reportService.getAccountReport(request);
	}

	@PostMapping("/api/report/monthly-summary")
	public CommonResponse getMonthlySummary(@RequestBody MonthlySummaryRequest request) {

		return reportService.getMonthlySummary(request);
	}
}