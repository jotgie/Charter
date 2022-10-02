package com.charter.reward;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;


class PointsCalculatorTest {

    @ParameterizedTest
    @CsvSource({"50.50,0", "51.00,1", "100.99,50", "101.00,52", "120.00,90"})
    void shouldCalculatePoints(BigDecimal amount, int expectedReward) {
        // when
        int reward = PointsCalculator.calculatePoints(amount);

        // then
        assertEquals(expectedReward, reward);
    }

}