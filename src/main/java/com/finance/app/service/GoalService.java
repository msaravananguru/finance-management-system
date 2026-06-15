package com.finance.app.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finance.app.dto.CommonResponse;
import com.finance.app.dto.goal.CreateGoalRequest;
import com.finance.app.dto.goal.DeleteGoalRequest;
import com.finance.app.dto.goal.GoalContributionRequest;
import com.finance.app.dto.goal.GoalDetailsRequest;
import com.finance.app.dto.goal.GoalSummaryResponse;
import com.finance.app.dto.goal.UpdateGoalRequest;
import com.finance.app.entity.FinancialGoal;
import com.finance.app.repository.FinancialGoalRepository;

@Service
public class GoalService {

	@Autowired
	private FinancialGoalRepository goalRepository;

	public CommonResponse createGoal(CreateGoalRequest request) {

		if (request.getTargetAmount() == null || request.getTargetAmount().doubleValue() <= 0) {

			return new CommonResponse(false, "Target Amount Must Be Greater Than Zero", null);
		}

		boolean exists = goalRepository.existsByUserIdAndGoalNameAndStatus(1L, request.getGoalName(), "ACTIVE");

		if (exists) {

			return new CommonResponse(false, "Goal Already Exists", null);
		}

		FinancialGoal goal = new FinancialGoal();

		goal.setUserId(1L);

		goal.setGoalName(request.getGoalName());

		goal.setTargetAmount(request.getTargetAmount());

		goal.setSavedAmount(BigDecimal.ZERO);

		goal.setTargetDate(request.getTargetDate());

		goal.setStatus("ACTIVE");

		goalRepository.save(goal);

		return new CommonResponse(true, "Goal Created Successfully", goal);
	}

	public CommonResponse getAllGoals() {

		return new CommonResponse(true, "Goal List Fetched Successfully",
				goalRepository.findByUserIdAndStatus(1L, "ACTIVE"));
	}

	public CommonResponse getGoalDetails(GoalDetailsRequest request) {

		FinancialGoal goal = goalRepository.findById(request.getGoalId()).orElse(null);

		if (goal == null) {

			return new CommonResponse(false, "Goal Not Found", null);
		}

		return new CommonResponse(true, "Goal Found", goal);
	}

	public CommonResponse updateGoal(UpdateGoalRequest request) {

		FinancialGoal goal = goalRepository.findById(request.getGoalId()).orElse(null);

		if (goal == null) {

			return new CommonResponse(false, "Goal Not Found", null);
		}

		if ("DELETED".equals(goal.getStatus())) {

			return new CommonResponse(false, "Cannot Update Deleted Goal", null);
		}

		goal.setGoalName(request.getGoalName());

		goal.setTargetAmount(request.getTargetAmount());

		goal.setTargetDate(request.getTargetDate());

		goalRepository.save(goal);

		return new CommonResponse(true, "Goal Updated Successfully", goal);
	}

	public CommonResponse deleteGoal(DeleteGoalRequest request) {

		FinancialGoal goal = goalRepository.findById(request.getGoalId()).orElse(null);

		if (goal == null) {

			return new CommonResponse(false, "Goal Not Found", null);
		}

		if ("DELETED".equals(goal.getStatus())) {

			return new CommonResponse(false, "Goal Already Deleted", null);
		}

		goal.setStatus("DELETED");

		goalRepository.save(goal);

		return new CommonResponse(true, "Goal Deleted Successfully", null);
	}

	public CommonResponse contributeToGoal(GoalContributionRequest request) {

		FinancialGoal goal = goalRepository.findById(request.getGoalId()).orElse(null);

		if (goal == null) {

			return new CommonResponse(false, "Goal Not Found", null);
		}

		if ("DELETED".equals(goal.getStatus())) {

			return new CommonResponse(false, "Goal Not Found", null);
		}

		if (request.getAmount() == null || request.getAmount().doubleValue() <= 0) {

			return new CommonResponse(false, "Contribution Amount Must Be Greater Than Zero", null);
		}

		goal.setSavedAmount(goal.getSavedAmount().add(request.getAmount()));

		goalRepository.save(goal);

		return new CommonResponse(true, "Contribution Added Successfully", goal);
	}

	public CommonResponse getGoalSummary() {

		List<FinancialGoal> goals = goalRepository.findByUserIdAndStatus(1L, "ACTIVE");

		List<GoalSummaryResponse> result = new ArrayList<>();

		for (FinancialGoal goal : goals) {

			GoalSummaryResponse response = new GoalSummaryResponse();

			response.setGoalName(goal.getGoalName());

			response.setTargetAmount(goal.getTargetAmount());

			response.setSavedAmount(goal.getSavedAmount());

			response.setRemainingAmount(goal.getTargetAmount().subtract(goal.getSavedAmount()));

			BigDecimal completion = BigDecimal.ZERO;

			if (goal.getTargetAmount().compareTo(BigDecimal.ZERO) > 0) {

				completion = goal.getSavedAmount().multiply(BigDecimal.valueOf(100)).divide(goal.getTargetAmount(), 2,
						RoundingMode.HALF_UP);
			}

			response.setCompletionPercentage(completion);

			result.add(response);
		}

		return new CommonResponse(true, "Goal Summary Generated Successfully", result);
	}
}