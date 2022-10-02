package com.charter.transaction;

import com.charter.TransactionRequest;
import com.charter.TransactionResponse;
import com.charter.customer.Customer;
import com.charter.customer.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionMapperTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private TransactionMapperImpl mapper;

    @Test
    void shouldMapApiToDomain() {
        // given
        Customer customer = new Customer();
        customer.setId(18L);
        customer.setEmail("john@doe.com");
        customer.setFirstName("John");
        customer.setLastName("Doe");

        Transaction expectedTransaction = new Transaction();
        expectedTransaction.setCustomer(customer);
        expectedTransaction.setAmount(BigDecimal.valueOf(12));

        when(customerRepository.findByEmail("john@doe.com")).thenReturn(customer);


        TransactionRequest request = new TransactionRequest()
                .customerEmail("john@doe.com")
                .amount(BigDecimal.valueOf(12));

        // when
        Transaction result = mapper.apiToDomain(request);

        // then
        assertEquals(expectedTransaction, result);
    }

    @Test
    void shouldMapDomainToApi() {
        // given
        Customer customer = new Customer();
        customer.setId(18L);
        customer.setEmail("john@doe.com");
        customer.setFirstName("John");
        customer.setLastName("Doe");

        Transaction transaction = new Transaction();
        transaction.setCustomer(customer);
        transaction.setAmount(BigDecimal.valueOf(12));
        String transactionUuid = UUID.randomUUID().toString();
        transaction.setUuid(transactionUuid);
        LocalDate creationDate = LocalDate.now();
        transaction.setCreationDate(creationDate);

        TransactionResponse expectedResponse = new TransactionResponse()
                .customerEmail("john@doe.com").amount(BigDecimal.valueOf(12)).uuid(transactionUuid).creationDate(creationDate);


        // when
        TransactionResponse result = mapper.domainToApi(transaction);

        // then
        assertEquals(expectedResponse, result);
    }

}