package com.charter.reward;

import com.charter.RewardResponse;
import com.charter.exceptions.ValidationException;
import org.springframework.stereotype.Component;

import static com.charter.EmailValidation.validateEmail;

@Component
public class RewardAdapter {

    public final RewardService service;

    public RewardAdapter(RewardService service) {
        this.service = service;
    }

    RewardResponse calculateMonthlyReward(String email) {
        validateInput(email);
        return new RewardResponse().amountOfPoints(service.calculateMonthlyReward(email));
    }

    RewardResponse calculateTotalReward(String email) {
        validateInput(email);
        return new RewardResponse().amountOfPoints(service.calculateTotalReward(email));
    }

    private void validateInput(String email) {
        if (email == null) {
            throw new ValidationException("Email not provided");
        } else if (!validateEmail(email)) {
            throw new ValidationException("Invalid email: " + email);
        }
    }

}
