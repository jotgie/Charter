package com.charter.reward;

import com.charter.RewardResponse;
import com.charter.exceptions.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        when(rewardService.calculateMonthlyReward(email)).thenReturn(1);

        // when
        RewardResponse result = adapter.calculateMonthlyReward(email);

        // then
        assertEquals(new RewardResponse().amountOfPoints(1), result);
    }

    @Test
    void shouldCallTotalRewardCalculation() {
        // given
        String email = "john@doe.com";
        when(rewardService.calculateTotalReward(email)).thenReturn(1);

        // when
        RewardResponse result = adapter.calculateTotalReward(email);

        // then
        assertEquals(new RewardResponse().amountOfPoints(1), result);
    }

    @Test
    void shouldThrowValidationExceptionWhenEmailNotProvided() {
        // when + then
        assertThrows(ValidationException.class, () -> adapter.calculateTotalReward(null));
    }

}