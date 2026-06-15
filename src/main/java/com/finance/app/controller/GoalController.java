package com.finance.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finance.app.dto.CommonResponse;
import com.finance.app.dto.goal.CreateGoalRequest;
import com.finance.app.dto.goal.DeleteGoalRequest;
import com.finance.app.dto.goal.GoalContributionRequest;
import com.finance.app.dto.goal.GoalDetailsRequest;
import com.finance.app.dto.goal.UpdateGoalRequest;
import com.finance.app.service.GoalService;

@RestController
@RequestMapping("/api/goal")
public class GoalController {

    @Autowired
    private GoalService goalService;

    @PostMapping("/create")
    public CommonResponse createGoal(
            @RequestBody CreateGoalRequest request) {

        return goalService.createGoal(request);
    }

    @PostMapping("/list")
    public CommonResponse getAllGoals() {

        return goalService.getAllGoals();
    }

    @PostMapping("/details")
    public CommonResponse getGoalDetails(
            @RequestBody GoalDetailsRequest request) {

        return goalService.getGoalDetails(request);
    }

    @PostMapping("/update")
    public CommonResponse updateGoal(
            @RequestBody UpdateGoalRequest request) {

        return goalService.updateGoal(request);
    }

    @PostMapping("/delete")
    public CommonResponse deleteGoal(
            @RequestBody DeleteGoalRequest request) {

        return goalService.deleteGoal(request);
    }

    @PostMapping("/contribute")
    public CommonResponse contributeToGoal(
            @RequestBody GoalContributionRequest request) {

        return goalService.contributeToGoal(request);
    }

    @PostMapping("/summary")
    public CommonResponse getGoalSummary() {

        return goalService.getGoalSummary();
    }
}