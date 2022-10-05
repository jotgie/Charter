package com.charter.customer;

import com.charter.exceptions.CustomerNotFoundException;
import com.charter.reward.RewardService;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService service;

    @Test
    void shouldCreateCustomer() {
        // given
        String email = "john@doe.com";
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setFirstName("John");
        customer.setLastName("Doe");


        Customer savedCustomer = new Customer();
        savedCustomer.setId(18L);
        savedCustomer.setEmail(email);
        savedCustomer.setFirstName("John");
        savedCustomer.setLastName("Doe");

        when(customerRepository.save(customer)).thenReturn(savedCustomer);

        // when
        Customer result = service.createCustomer(customer);

        // then
        assertEquals(savedCustomer, result);
    }

    @Test
    void shouldUpdateCustomer() {
        // given
        String email = "john@doe.com";
        Customer newCustomer = new Customer();
        String newEmail = "jane@doe.com";
        newCustomer.setEmail(newEmail);
        newCustomer.setFirstName("Jane");
        newCustomer.setLastName("Doe");

        Customer existingCustomer = new Customer();
        existingCustomer.setId(18L);
        existingCustomer.setEmail(email);
        existingCustomer.setFirstName("John");
        existingCustomer.setLastName("Doe");

        Customer updatedCustomer = new Customer();
        updatedCustomer.setId(18L);
        updatedCustomer.setEmail(newEmail);
        updatedCustomer.setFirstName("Jane");
        updatedCustomer.setLastName("Doe");

        when(customerRepository.findByEmail(email)).thenReturn(existingCustomer);
        when(customerRepository.save(newCustomer)).thenReturn(updatedCustomer);

        // when
        Customer result = service.updateCustomer(email, newCustomer);

        // then
        assertEquals(updatedCustomer, result);
    }

    @Test
    void shouldThrowCustomerNotFoundException() {
        // given
        String email = "john@doe.com";
        Customer newCustomer = new Customer();
        String newEmail = "jane@doe.com";
        newCustomer.setEmail(newEmail);
        newCustomer.setFirstName("Jane");
        newCustomer.setLastName("Doe");

        when(customerRepository.findByEmail(email)).thenReturn(null);

        // when + then
        assertThrows(CustomerNotFoundException.class, () -> service.updateCustomer(email, newCustomer));
    }


}