package com.charter.reward;

import com.charter.RewardResponse;
import com.charter.TotalRewardResponse;
import com.charter.exceptions.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RewardAdapterTest {

    @Mock
    private RewardService rewardService;

    @InjectMocks
    private RewardAdapter adapter;

    @Test
    void shouldCallMonthlyRewardCalculation() {
        // given
        String email = "john@doe.com";
        Map<String, Integer> pointsMap = Collections.singletonMap("2022.11", 111);
        when(rewardService.calculateMonthlyReward(email)).thenReturn(pointsMap);
        RewardResponse expected = new RewardResponse();
        expected.put("2022.11", 111);

        // when
        RewardResponse result = adapter.calculateMonthlyReward(email);

        // then
        assertEquals(expected, result);
    }

    @Test
    void shouldCallTotalRewardCalculation() {
        // given
        String email = "john@doe.com";
        when(rewardService.calculateTotalReward(email)).thenReturn(1);

        // when
        TotalRewardResponse result = adapter.calculateTotalReward(email);

        // then
        assertEquals(new TotalRewardResponse().amountOfPoints(1), result);
    }

    @Test
    void shouldThrowValidationExceptionWhenEmailNotProvided() {
        // when + then
        assertThrows(ValidationException.class, () -> adapter.calculateTotalReward(null));
    }

}