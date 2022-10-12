package com.charter.reward;

import com.charter.transaction.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static com.charter.reward.PointsCalculator.calculatePoints;

@Service
public class RewardService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RewardService.class);

    private final TransactionService transactionService;

    public RewardService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    Map<String, Integer> calculateMonthlyReward(String email) {
        LOGGER.info("Calculating monthly reward for " + email);
        return transactionService.getAllTransactionsByEmail(email)
                .stream()
                .map(transaction ->
                        new TransactionPoints(transaction.getCreationDate(), calculatePoints(transaction.getAmount())))
                .filter(points -> points.collectedPoints != 0)
                .collect(Collectors.groupingBy(points -> YearMonth.from(points.createdDate).toString(),
                        TreeMap::new,
                        Collectors.summingInt(points -> points.collectedPoints)));
    }

    int calculateTotalReward(String email) {
        LOGGER.info("Calculating total reward for " + email);
        return transactionService.getAllTransactionsByEmail(email)
                .stream()
                .mapToInt(t -> calculatePoints(t.getAmount()))
                .sum();
    }

    private record TransactionPoints(LocalDate createdDate, int collectedPoints) {
    }

}
