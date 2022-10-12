package com.charter.transaction;

import com.charter.TransactionRequest;
import com.charter.TransactionResponse;
import com.charter.customer.Customer;
import com.charter.customer.CustomerRepository;
import com.charter.exceptions.CustomerNotFoundException;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class TransactionMapper {

    @Autowired
    CustomerRepository customerRepository;

    @Mapping(target = "customerEmail", source = "customer.email")
    abstract TransactionResponse domainToApi(Transaction transaction);

    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    abstract Transaction apiToDomain(TransactionRequest request);

    @AfterMapping
    void enrichDomainWithCustomer(TransactionRequest request, @MappingTarget Transaction transaction) {
        String customerEmail = request.getCustomerEmail();
        Customer customer = customerRepository.findByEmail(customerEmail);
        if (customer == null) {
            throw new CustomerNotFoundException(customerEmail);
        }
        transaction.setCustomer(customer);
    }

}
