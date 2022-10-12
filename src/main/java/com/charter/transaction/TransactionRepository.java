package com.charter.transaction;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findAllByCustomerEmail(String customerEmail);

    Transaction findByUuid(String uuid);

}
