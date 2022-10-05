package com.charter.transaction;

import com.charter.TransactionRequest;
import com.charter.TransactionResponse;
import com.charter.UpdateTransactionRequest;
import com.charter.exceptions.ValidationException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static com.charter.EmailValidation.validateEmail;

@Component
class TransactionAdapter {

    private final TransactionMapper mapper;
    private final TransactionService service;

    TransactionAdapter(TransactionMapper mapper, TransactionService service) {
        this.mapper = mapper;
        this.service = service;
    }

    TransactionResponse createTransaction(TransactionRequest request) {
        validateCustomerEmail(request.getCustomerEmail());
        validateAmount(request.getAmount());
        Transaction transaction = mapper.apiToDomain(request);
        transaction.setUuid(UUID.randomUUID().toString());
        transaction.setCreationDate(LocalDate.now());
        Transaction createdTransaction = service.createTransaction(transaction);
        return mapper.domainToApi(createdTransaction);
    }

    private void validateCustomerEmail(String email) {
        if (email == null) {
            throw new ValidationException("Email not provided");
        } else if (!validateEmail(email)) {
            throw new ValidationException("Invalid email: " + email);
        }
    }

    TransactionResponse updateTransaction(String uuid, UpdateTransactionRequest request) {
        BigDecimal amount = request.getAmount();
        validateAmount(amount);
        Transaction createdTransaction = service.updateTransactionAmount(uuid, amount);
        return mapper.domainToApi(createdTransaction);
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null) {
            throw new ValidationException("Amount not provided");
        }
        if (amount.compareTo(BigDecimal.ONE) < 0) {
            throw new ValidationException("Amount cannot be less than 0");
        }
    }

}
