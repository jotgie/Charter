package com.charter.transaction;

import com.charter.exceptions.TransactionNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionService.class);

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getAllTransactionsByEmail(String email) {
        return transactionRepository.findAllByCustomerEmail(email);
    }

    public Transaction createTransaction(Transaction transaction) {
        LOGGER.info("Creating a transaction for customer " + transaction.getCustomer().getEmail());
        return transactionRepository.save(transaction);
    }

    public Transaction updateTransactionAmount(String uuid, BigDecimal newAmount) {
        LOGGER.info("Updating transaction " + uuid);
        Transaction transaction = transactionRepository.findByUuid(uuid);
        if (transaction == null) {
            LOGGER.warn("Transaction not found: " + uuid);
            throw new TransactionNotFoundException(uuid);
        }
        transaction.setAmount(newAmount);
        return transactionRepository.save(transaction);
    }

}
