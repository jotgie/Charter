package com.charter.reward;

import com.charter.RewardResponse;
import com.charter.TotalRewardResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reward/v1")
public class RewardController {

    private final RewardAdapter rewardAdapter;

    public RewardController(RewardAdapter rewardAdapter) {
        this.rewardAdapter = rewardAdapter;
    }

    @GetMapping("/{email}/total")
    public TotalRewardResponse getTotalRewardByEmail(@PathVariable("email") String email) {
        return rewardAdapter.calculateTotalReward(email);
    }

    @GetMapping("/{email}/monthly")
    public RewardResponse getMonthlyRewardByEmail(@PathVariable("email") String email) {
        return rewardAdapter.calculateMonthlyReward(email);
    }
}
