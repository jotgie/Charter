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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        transaction1.setCreationDate(LocalDate.now().minusMonths(1));
        transaction1.setAmount(BigDecimal.valueOf(5));

        Transaction transaction2 = new Transaction();
        transaction2.setAmount(BigDecimal.valueOf(51));
        transaction2.setCustomer(customer);
        transaction2.setCreationDate(LocalDate.now());

        Transaction transaction3 = new Transaction();
        transaction3.setAmount(BigDecimal.valueOf(80));
        transaction3.setCustomer(customer);
        transaction3.setCreationDate(LocalDate.now());

        Transaction transaction4 = new Transaction();
        transaction4.setAmount(BigDecimal.valueOf(80));
        transaction4.setCustomer(customer);
        transaction4.setCreationDate(LocalDate.now().minusMonths(2));

        List<Transaction> allTransactions = Arrays.asList(transaction1, transaction2, transaction3, transaction4);
        when(transactionService.getAllTransactionsByEmail(email)).thenReturn(allTransactions);

        Map<String , Integer> expected = new HashMap<>();
        expected.put("2022-08", 30);
        expected.put("2022-10", 31);

        // when
        Map<String, Integer> result = service.calculateMonthlyReward(email);

        // then
        assertEquals(expected, result);
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