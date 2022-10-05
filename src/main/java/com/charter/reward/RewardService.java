package com.charter.reward;

import com.charter.transaction.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static com.charter.reward.PointsCalculator.calculatePoints;

@Service
public class RewardService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RewardService.class);

    private final TransactionService transactionService;

    public RewardService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    int calculateMonthlyReward(String email) {
        LOGGER.info("Calculating monthly reward for " + email);
        return transactionService.getMonthlyTransactionsByEmail(email)
                .stream()
                .mapToInt(t -> calculatePoints(t.getAmount()))
                .sum();
    }

    int calculateTotalReward(String email) {
        LOGGER.info("Calculating total reward for " + email);
        return transactionService.getAllTransactionsByEmail(email)
                .stream()
                .mapToInt(t -> calculatePoints(t.getAmount()))
                .sum();
    }

}
