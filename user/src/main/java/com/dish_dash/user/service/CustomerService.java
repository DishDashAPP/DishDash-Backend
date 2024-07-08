package com.dish_dash.user.service;

import com.dish_dash.user.adapters.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer modifyProfile(Customer customer) {
        // Assuming customerRepository.save() updates the profile if the customer already exists
        return customerRepository.save(customer);
    }

    public boolean createCustomer(Customer customer) {
        // Assuming customerRepository.save() returns the saved entity
        Customer savedCustomer = customerRepository.save(customer);
        return savedCustomer != null;
    }

    public String getCustomerAddress(String customerID) {
        // Logic to fetch CustomerAddress for the given customerID
        Customer customer = customerRepository.findById(customerID).orElse(null);
        if (customer != null) {
            return customer.getAddress();
        }
        return null;
    }

    public Customer getUserProfile(String customerID) {
        // Assuming customerRepository.findById() returns an Optional<Customer>
        return customerRepository.findById(customerID).orElse(null);
    }
}
