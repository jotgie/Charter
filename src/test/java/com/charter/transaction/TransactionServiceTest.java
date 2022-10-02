package com.charter.transaction;

import com.charter.customer.Customer;
import com.charter.exceptions.TransactionNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService service;

    @Test
    void shouldCreateTransaction() {
        // given
        String email = "john@doe.com";
        Transaction transaction = new Transaction();
        transaction.setAmount(BigDecimal.valueOf(12));
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        transaction.setCustomer(customer);

        when(transactionRepository.save(transaction)).thenReturn(transaction);

        // when
        Transaction result = service.createTransaction(transaction);

        // then
        assertEquals(transaction, result);
    }

    @Test
    void shouldUpdateTransaction() {
        // given
        String email = "john@doe.com";
        Transaction transaction = new Transaction();
        transaction.setAmount(BigDecimal.valueOf(12));
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        transaction.setCustomer(customer);

        String uuid = UUID.randomUUID().toString();
        Transaction existingTransaction = new Transaction();
        existingTransaction.setCreationDate(LocalDate.now());
        existingTransaction.setUuid(uuid);
        existingTransaction.setCustomer(customer);
        existingTransaction.setId(18L);
        existingTransaction.setAmount(BigDecimal.valueOf(15));
        when(transactionRepository.findByUuid(uuid)).thenReturn(existingTransaction);

        Transaction expectedTransaction = new Transaction();
        expectedTransaction.setCreationDate(LocalDate.now());
        expectedTransaction.setUuid(uuid);
        expectedTransaction.setCustomer(customer);
        expectedTransaction.setId(18L);
        expectedTransaction.setAmount(BigDecimal.valueOf(12));

        when(transactionRepository.save(expectedTransaction)).thenReturn(expectedTransaction);

        // when
        Transaction result = service.updateTransaction(uuid, transaction);

        // then
        assertEquals(expectedTransaction, result);
    }

    @Test
    void shouldThrowTransactionNotFoundException() {
        // given
        String email = "john@doe.com";
        Transaction transaction = new Transaction();
        transaction.setAmount(BigDecimal.valueOf(12));
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        transaction.setCustomer(customer);

        String uuid = UUID.randomUUID().toString();
        when(transactionRepository.findByUuid(uuid)).thenReturn(null);

        // when + then
        assertThrows(TransactionNotFoundException.class, () -> service.updateTransaction(uuid, transaction));
    }

    @Test
    void shouldReturnMonthlyTransactions() {
        // given
        String email = "john@doe.com";
        Transaction transaction = new Transaction();
        transaction.setAmount(BigDecimal.valueOf(12));
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        transaction.setCustomer(customer);

        LocalDate dateBefore1Month = LocalDate.now().minusMonths(1);
        List<Transaction> transactions = Collections.singletonList(transaction);
        when(transactionRepository.findAllByCustomerEmailAndCreationDateGreaterThan(email, dateBefore1Month)).thenReturn(transactions);

        // when
        List<Transaction> result = service.getMonthlyTransactionsByEmail(email);

        // then
        assertEquals(transactions, result);
    }

    @Test
    void shouldReturnAllTransactions() {
        // given
        String email = "john@doe.com";
        Transaction transaction = new Transaction();
        transaction.setAmount(BigDecimal.valueOf(12));
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        transaction.setCustomer(customer);

        List<Transaction> transactions = Collections.singletonList(transaction);
        when(transactionRepository.findAllByCustomerEmail(email)).thenReturn(transactions);

        // when
        List<Transaction> result = service.getAllTransactionsByEmail(email);

        // then
        assertEquals(transactions, result);
    }
}