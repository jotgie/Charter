package com.charter.transaction;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findAllByCustomerEmailAndCreationDateGreaterThan(String customerEmail, LocalDate creationDate);

    List<Transaction> findAllByCustomerEmail(String customerEmail);

    Transaction findByUuid(String uuid);

}
