package com.charter.transaction;

import com.charter.TransactionRequest;
import com.charter.TransactionResponse;
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
        validateRequest(request);
        Transaction transaction = mapper.apiToDomain(request);
        transaction.setUuid(UUID.randomUUID().toString());
        transaction.setCreationDate(LocalDate.now());
        Transaction createdTransaction = service.createTransaction(transaction);
        return mapper.domainToApi(createdTransaction);
    }

    TransactionResponse updateTransaction(String uuid, TransactionRequest request) {
        validateRequest(request);
        Transaction transaction = mapper.apiToDomain(request);
        Transaction createdTransaction = service.updateTransaction(uuid, transaction);
        return mapper.domainToApi(createdTransaction);
    }

    private void validateRequest(TransactionRequest request) {
        if (request.getCustomerEmail() == null) {
            throw new ValidationException("Email not provided");
        } else if (!validateEmail(request.getCustomerEmail())) {
            throw new ValidationException("Invalid email: " + request.getCustomerEmail());
        }
        if (request.getAmount() == null) {
            throw new ValidationException("Amount not provided");
        }
        if (request.getAmount().compareTo(BigDecimal.ONE) < 0) {
            throw new ValidationException("Amount cannot be less than 0");
        }
    }

}
