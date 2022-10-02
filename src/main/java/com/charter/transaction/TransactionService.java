package com.charter.transaction;

import com.charter.exceptions.TransactionNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getMonthlyTransactionsByEmail(String email) {
        LocalDate dateBefore1Month = LocalDate.now().minusMonths(1);
        return transactionRepository.findAllByCustomerEmailAndCreationDateGreaterThan(email, dateBefore1Month);
    }

    public List<Transaction> getAllTransactionsByEmail(String email) {
        return transactionRepository.findAllByCustomerEmail(email);
    }

    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public Transaction updateTransaction(String uuid, Transaction transaction) {
        Transaction existingTransaction = transactionRepository.findByUuid(uuid);
        if (existingTransaction == null) {
            throw new TransactionNotFoundException(uuid);
        }
        transaction.setId(existingTransaction.getId());
        transaction.setCreationDate(existingTransaction.getCreationDate());
        transaction.setUuid(uuid);
        return transactionRepository.save(transaction);
    }

}
