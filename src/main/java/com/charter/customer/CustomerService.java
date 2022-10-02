package com.charter.customer;

import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    Customer getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    Customer updateCustomer(String email, Customer customer) {
        Customer existingCustomer = customerRepository.findByEmail(email);
        customer.setId(existingCustomer.getId());
        return customerRepository.save(customer);
    }
}
