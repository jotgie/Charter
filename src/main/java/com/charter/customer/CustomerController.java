package com.charter.customer;

import com.charter.CustomerApi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer/v1")
public class CustomerController {

    private final CustomerAdapter customerAdapter;

    public CustomerController(CustomerAdapter customerAdapter) {
        this.customerAdapter = customerAdapter;
    }

    @GetMapping("/{email}")
    public CustomerApi getCustomerByEmail(@PathVariable("email") String email) {
        return customerAdapter.getCustomer(email);
    }

    @PostMapping
    public CustomerApi createCustomer(@RequestBody CustomerApi request) {
        return customerAdapter.createCustomer(request);
    }

    @PutMapping("/{email}")
    public CustomerApi updateCustomer(@PathVariable("email") String email, @RequestBody CustomerApi request) {
        return customerAdapter.updateCustomer(email, request);
    }

}
