package com.charter.customer;

import com.charter.CustomerApi;
import com.charter.exceptions.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerAdapterTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerAdapter adapter;

    @Test
    void shouldCreateCustomer() {
        // given
        String email = "john@doe.com";
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setFirstName("John");
        customer.setLastName("Doe");

        Customer savedCustomer = new Customer();
        savedCustomer.setId(1);
        savedCustomer.setEmail(email);
        savedCustomer.setFirstName("John");
        savedCustomer.setLastName("Doe");


        when(customerService.createCustomer(customer)).thenReturn(savedCustomer);
        CustomerApi customerApi = new CustomerApi().email(email)
                .firstName("John")
                .lastName("Doe");
        when(customerMapper.apiToDomain(customerApi)).thenReturn(customer);
        when(customerMapper.domainToApi(savedCustomer)).thenReturn(customerApi);

        // when
        CustomerApi result = adapter.createCustomer(customerApi);

        // then
        assertEquals(customerApi, result);
    }

    @Test
    void shouldUpdateCustomer() {
        // given
        String email = "john@doe.com";
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setFirstName("John");
        customer.setLastName("Doe");

        Customer savedCustomer = new Customer();
        savedCustomer.setId(1);
        savedCustomer.setEmail(email);
        savedCustomer.setFirstName("John");
        savedCustomer.setLastName("Doe");


        when(customerService.updateCustomer(email, customer)).thenReturn(savedCustomer);
        CustomerApi customerApi = new CustomerApi().email(email)
                .firstName("John")
                .lastName("Doe");
        when(customerMapper.apiToDomain(customerApi)).thenReturn(customer);
        when(customerMapper.domainToApi(savedCustomer)).thenReturn(customerApi);

        // when
        CustomerApi result = adapter.updateCustomer(email, customerApi);

        // then
        assertEquals(customerApi, result);
    }

    @Test
    void shouldReturnCustomer() {
        // given
        String email = "john@doe.com";

        Customer savedCustomer = new Customer();
        savedCustomer.setId(1);
        savedCustomer.setEmail(email);
        savedCustomer.setFirstName("John");
        savedCustomer.setLastName("Doe");


        when(customerService.getCustomerByEmail(email)).thenReturn(savedCustomer);
        CustomerApi customerApi = new CustomerApi().email(email)
                .firstName("John")
                .lastName("Doe");
        when(customerMapper.domainToApi(savedCustomer)).thenReturn(customerApi);

        // when
        CustomerApi result = adapter.getCustomer(email);

        // then
        assertEquals(customerApi, result);
    }

    @Test
    void shouldThrowValidationExceptionWhenEmailNotProvided() {
        // given
        CustomerApi customerApi = new CustomerApi().email(null).firstName("John").lastName("Doe");

        // when + then
        assertThrows(ValidationException.class, () -> adapter.createCustomer(customerApi));
    }

    @Test
    void shouldThrowValidationExceptionWhenFirstNameNotProvided() {
        // given
        CustomerApi customerApi = new CustomerApi().email("john@doe.com").firstName(null).lastName("Doe");

        // when + then
        assertThrows(ValidationException.class, () -> adapter.createCustomer(customerApi));
    }

    @Test
    void shouldThrowValidationExceptionWhenLastNameNotProvided() {
        // given
        CustomerApi customerApi = new CustomerApi().email("john@doe.com").firstName("John").lastName(null);

        // when + then
        assertThrows(ValidationException.class, () -> adapter.createCustomer(customerApi));
    }


}