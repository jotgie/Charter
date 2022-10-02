package com.charter.reward;

import com.charter.customer.Customer;
import com.charter.transaction.Transaction;
import com.charter.transaction.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RewardServiceTest {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private RewardService service;

    @Test
    void shouldCalculateMonthlyReward() {
        // given
        String email = "john@doe.com";
        Transaction transaction1 = new Transaction();
        transaction1.setAmount(BigDecimal.valueOf(12));
        Customer customer = new Customer();
        customer.setId(18L);
        customer.setEmail(email);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        transaction1.setCustomer(customer);
        transaction1.setCreationDate(LocalDate.now());

        Transaction transaction2 = new Transaction();
        transaction2.setAmount(BigDecimal.valueOf(51));
        transaction2.setCustomer(customer);
        transaction2.setCreationDate(LocalDate.now());

        when(transactionService.getMonthlyTransactionsByEmail(email)).thenReturn(Arrays.asList(transaction1, transaction2));

        // when
        int result = service.calculateMonthlyReward(email);

        // then
        assertEquals(1, result);
    }

    @Test
    void shouldCalculateTotalReward() {
        // given
        String email = "john@doe.com";
        Transaction transaction1 = new Transaction();
        transaction1.setAmount(BigDecimal.valueOf(12));
        Customer customer = new Customer();
        customer.setId(18L);
        customer.setEmail(email);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        transaction1.setCustomer(customer);
        transaction1.setCreationDate(LocalDate.now());

        Transaction transaction2 = new Transaction();
        transaction2.setAmount(BigDecimal.valueOf(51));
        transaction2.setCustomer(customer);
        transaction2.setCreationDate(LocalDate.now());

        when(transactionService.getAllTransactionsByEmail(email)).thenReturn(Arrays.asList(transaction1, transaction2));

        // when
        int result = service.calculateTotalReward(email);

        // then
        assertEquals(1, result);
    }
}