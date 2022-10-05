package com.charter.transaction;

import com.charter.TransactionRequest;
import com.charter.TransactionResponse;
import com.charter.UpdateTransactionRequest;
import com.charter.exceptions.ValidationException;
import com.charter.customer.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionAdapterTest {

    @Mock
    private TransactionService transactionService;

    @Mock
    private TransactionMapper transactionMapper;

    @InjectMocks
    private TransactionAdapter adapter;

    @Test
    void shouldCreateTransaction() {
        // given
        String email = "john@doe.com";

        Customer customer = new Customer();
        customer.setId(18L);
        customer.setEmail(email);
        customer.setFirstName("John");
        customer.setLastName("Doe");

        Transaction transaction = new Transaction();
        transaction.setCustomer(customer);
        transaction.setAmount(BigDecimal.valueOf(12));

        Transaction savedTransaction = new Transaction();
        savedTransaction.setCustomer(customer);
        savedTransaction.setAmount(BigDecimal.valueOf(12));
        String transactionUuid = UUID.randomUUID().toString();
        savedTransaction.setUuid(transactionUuid);
        LocalDate creationDate = LocalDate.now();
        savedTransaction.setCreationDate(creationDate);

        when(transactionService.createTransaction(transaction)).thenReturn(savedTransaction);
        TransactionRequest request = new TransactionRequest().amount(BigDecimal.valueOf(12)).customerEmail(email);

        when(transactionMapper.apiToDomain(request)).thenReturn(transaction);
        TransactionResponse response = new TransactionResponse().amount(BigDecimal.valueOf(12)).creationDate(creationDate).uuid(transactionUuid).customerEmail(email);
        when(transactionMapper.domainToApi(savedTransaction)).thenReturn(response);

        // when
        TransactionResponse result = adapter.createTransaction(request);

        // then
        assertEquals(response, result);
    }

    @Test
    void shouldUpdateTransaction() {
        // given
        String email = "john@doe.com";

        Customer customer = new Customer();
        customer.setId(18L);
        customer.setEmail(email);
        customer.setFirstName("John");
        customer.setLastName("Doe");

        Transaction transaction = new Transaction();
        transaction.setCustomer(customer);
        transaction.setAmount(BigDecimal.valueOf(12));
        String transactionUuid = UUID.randomUUID().toString();
        transaction.setUuid(transactionUuid);
        LocalDate creationDate = LocalDate.now();
        transaction.setCreationDate(creationDate);

        when(transactionService.updateTransactionAmount(transactionUuid, BigDecimal.valueOf(12))).thenReturn(transaction);
        UpdateTransactionRequest request = new UpdateTransactionRequest().amount(BigDecimal.valueOf(12));

        TransactionResponse response = new TransactionResponse().amount(BigDecimal.valueOf(12)).creationDate(creationDate).uuid(transactionUuid).customerEmail(email);
        when(transactionMapper.domainToApi(transaction)).thenReturn(response);

        // when
        TransactionResponse result = adapter.updateTransaction(transactionUuid, request);

        // then
        assertEquals(response, result);
    }

    @Test
    void shouldThrowValidationExceptionWhenEmailNotProvided() {
        // given
        TransactionRequest request = new TransactionRequest().amount(BigDecimal.valueOf(12));

        // when + then
        assertThrows(ValidationException.class, () -> adapter.createTransaction(request));
    }

    @Test
    void shouldThrowValidationExceptionWhenAmountNotProvided() {
        // given
        TransactionRequest request = new TransactionRequest().customerEmail("john@doe.com");

        // when + then
        assertThrows(ValidationException.class, () -> adapter.createTransaction(request));
    }

}