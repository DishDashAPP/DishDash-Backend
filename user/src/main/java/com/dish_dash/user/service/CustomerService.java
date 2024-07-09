package com.dish_dash.user.service;

import com.dish_dash.user.adapters.repository.CustomerRepository;
import com.dish_dash.user.domain.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer modifyProfile(Customer customer) {
        return customerRepository.save(customer);
    }

    public boolean createCustomer(Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);
        return savedCustomer != null;
    }

    public String getCustomerAddress(String customerID) {
        Customer customer = customerRepository.findById(customerID).orElse(null);
        if (customer != null) {
            return customer.getAddress();
        }
        return null;
    }

    public Customer getUserProfile(String customerID) {
        return customerRepository.findById(customerID).orElse(null);
    }
}
