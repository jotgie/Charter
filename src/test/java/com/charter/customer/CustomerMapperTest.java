package com.charter.customer;

import com.charter.CustomerApi;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerMapperTest {

    private final CustomerMapper mapper = new CustomerMapperImpl();

    @Test
    void shouldMapApiToDomain() {
        // given
        Customer expectedCustomer = new Customer();
        expectedCustomer.setEmail("john@doe.com");
        expectedCustomer.setFirstName("John");
        expectedCustomer.setLastName("Doe");

        CustomerApi customerApi = new CustomerApi()
                .email("john@doe.com")
                .firstName("John")
                .lastName("Doe");

        // when
        Customer result = mapper.apiToDomain(customerApi);

        // then
        assertEquals(expectedCustomer, result);
    }

    @Test
    void shouldMapDomainToApi() {
        // given
        Customer customer = new Customer();
        customer.setEmail("john@doe.com");
        customer.setFirstName("John");
        customer.setLastName("Doe");

        CustomerApi expectedApi = new CustomerApi()
                .email("john@doe.com")
                .firstName("John")
                .lastName("Doe");


        // when
        CustomerApi result = mapper.domainToApi(customer);

        // then
        assertEquals(expectedApi, result);
    }

}