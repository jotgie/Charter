package com.charter.customer;

import com.charter.exceptions.CustomerNotFoundException;
import com.charter.reward.RewardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RewardService.class);

    private final CustomerRepository customerRepository;

    CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    Customer getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    Customer createCustomer(Customer customer) {
        LOGGER.info("Creating a new customer: " + customer.getEmail());
        return customerRepository.save(customer);
    }

    Customer updateCustomer(String email, Customer customer) {
        LOGGER.info("Updating customer: " + email);
        Customer existingCustomer = customerRepository.findByEmail(email);
        if (existingCustomer == null) {
            LOGGER.warn("Customer not found: " + email);
            throw new CustomerNotFoundException(email);
        }
        customer.setId(existingCustomer.getId());
        return customerRepository.save(customer);
    }
}
