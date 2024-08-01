package com.dish_dash.user.service;

import com.dishDash.common.dto.CustomerDto;
import com.dish_dash.user.adapters.repository.CustomerRepository;
import com.dish_dash.user.domain.mapper.UserMapper;
import com.dish_dash.user.domain.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

  private final CustomerRepository customerRepository;

  public Boolean modifyProfile(long id, CustomerDto customerDto) {
    // Check if the customer exists
    return customerRepository
            .findById(id)
            .map(
                    customer -> {
                      // Modify existing customer details
                      customerRepository.modify(
                              customerDto.getFirstName(),
                              customerDto.getLastName(),
                              customerDto.getAddress(),
                              customerDto.getPhoneNumber(),
                              customer.getId());
                      return true;
                    })
            .orElseGet(() -> {
              // Insert new customer if not found
              Customer newCustomer = UserMapper.INSTANCE.dtoToCustomer(customerDto);
              newCustomer.setId(id);
              customerRepository.save(newCustomer);
              return true;
            });
  }

  public void createCustomer(CustomerDto customerDto) {
    // Create a new customer from CustomerDto
    Customer newCustomer = UserMapper.INSTANCE.dtoToCustomer(customerDto);
    customerRepository.save(newCustomer);
  }

  public String getCustomerAddress(long customerID) {
    // Get customer address if exists
    return customerRepository.findById(customerID).map(Customer::getAddress).orElse(null);
  }

  public CustomerDto getUserProfile(long customerId) {
    // Get customer profile
    return customerRepository
            .findById(customerId)
            .map(UserMapper.INSTANCE::customerToDto)
            .orElse(null);
  }
}
