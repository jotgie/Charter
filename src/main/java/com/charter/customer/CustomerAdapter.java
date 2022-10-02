package com.charter.customer;

import com.charter.CustomerApi;
import com.charter.exceptions.ValidationException;
import org.springframework.stereotype.Component;

import static com.charter.EmailValidation.validateEmail;

@Component
public class CustomerAdapter {

    private final CustomerMapper mapper;
    private final CustomerService service;

    CustomerAdapter(CustomerMapper mapper, CustomerService service) {
        this.mapper = mapper;
        this.service = service;
    }

    CustomerApi createCustomer(CustomerApi customerApi) {
        validateRequest(customerApi);
        Customer customer = mapper.apiToDomain(customerApi);
        Customer createdCustomer = service.createCustomer(customer);
        return mapper.domainToApi(createdCustomer);
    }

    CustomerApi updateCustomer(String email, CustomerApi customerApi) {
        validateRequest(customerApi);
        Customer customer = mapper.apiToDomain(customerApi);
        Customer updatedCustomer = service.updateCustomer(email, customer);
        return mapper.domainToApi(updatedCustomer);
    }

    private void validateRequest(CustomerApi customerApi) {
        if (customerApi.getEmail() == null) {
            throw new ValidationException("Email not provided");
        } else if (!validateEmail(customerApi.getEmail())) {
            throw new ValidationException("Invalid email: " + customerApi.getEmail());
        }
        if (customerApi.getFirstName() == null) {
            throw new ValidationException("First name not provided");
        }
        if (customerApi.getLastName() == null) {
            throw new ValidationException("Last name not provided");
        }
    }

    CustomerApi getCustomer(String email) {
        Customer customer = service.getCustomerByEmail(email);
        return mapper.domainToApi(customer);
    }

}
