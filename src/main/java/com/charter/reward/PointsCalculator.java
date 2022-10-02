package com.charter.reward;

import java.math.BigDecimal;

final class PointsCalculator {

    static int calculatePoints(BigDecimal amount) {
        if (amount.intValue() > 100) {
            return amount.intValue() * 2 - 150;
        }
        if (amount.intValue() > 50) {
            return amount.intValue() - 50;
        }
        return 0;
    }

}
