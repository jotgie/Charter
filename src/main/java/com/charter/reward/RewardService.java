package com.charter.reward;

import com.charter.transaction.TransactionService;
import org.springframework.stereotype.Service;

import static com.charter.reward.PointsCalculator.calculatePoints;

@Service
public class RewardService {

    private final TransactionService transactionService;

    public RewardService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    int calculateMonthlyReward(String email) {
        return transactionService.getMonthlyTransactionsByEmail(email)
                .stream()
                .mapToInt(t -> calculatePoints(t.getAmount()))
                .sum();
    }

    int calculateTotalReward(String email) {
        return transactionService.getAllTransactionsByEmail(email)
                .stream()
                .mapToInt(t -> calculatePoints(t.getAmount()))
                .sum();
    }

}
