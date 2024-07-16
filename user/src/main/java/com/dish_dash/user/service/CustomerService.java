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

  public Boolean modifyProfile(Long id, CustomerDto customerDto) {
    return customerRepository
        .findById(id)
        .map(
            customer -> {
              UserMapper.INSTANCE.updateCustomerFromDto(customerDto, customer);
              customerRepository.save(customer);
              return true;
            })
        .orElse(false);
  }

  public void createCustomer(CustomerDto customerDto) {
    customerRepository.save(UserMapper.INSTANCE.dtoToCustomer(customerDto));
  }

  public String getCustomerAddress(Long customerID) {
    return customerRepository.findById(customerID).map(Customer::getAddress).orElse(null);
  }

  public CustomerDto getUserProfile(Long customerId) {
    return customerRepository
        .findById(customerId)
        .map(UserMapper.INSTANCE::customerToDto)
        .orElse(null);
  }
}
