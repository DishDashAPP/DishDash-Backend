package com.dish_dash.user.service;

import com.dishDash.common.dto.CustomerDto;
import com.dishDash.common.feign.authentication.AuthenticationApi;
import com.dish_dash.user.adapters.repository.CustomerRepository;
import com.dish_dash.user.domain.mapper.UserMapper;
import com.dish_dash.user.domain.model.Customer;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

  private final CustomerRepository customerRepository;
  private final AuthenticationApi authenticationApi;

  public Boolean modifyProfile(long id, CustomerDto customerDto) {
    return customerRepository
        .findById(id)
        .map(
            customer -> {
              customerRepository.modify(
                  customerDto.getFirstName(),
                  customerDto.getLastName(),
                  customerDto.getAddress(),
                  customerDto.getPhoneNumber(),
                  customer.getId());
              return true;
            })
        .orElseGet(
            () -> {
              Customer newCustomer = UserMapper.INSTANCE.dtoToCustomer(customerDto);
              newCustomer.setId(id);
              customerRepository.save(newCustomer);
              return true;
            });
  }

  public void createCustomer(CustomerDto customerDto) {
    Customer newCustomer = UserMapper.INSTANCE.dtoToCustomer(customerDto);
    customerRepository.save(newCustomer);
  }

  public String getCustomerAddress(long customerID) {
    return customerRepository.findById(customerID).map(Customer::getAddress).orElse(null);
  }

  public CustomerDto getUserProfile(long customerId) {
    String username = authenticationApi.getUsername(customerId);
    Optional<Customer> customerOptional = customerRepository.findById(customerId);
    if (customerOptional.isPresent()) {
      CustomerDto customerDto = UserMapper.INSTANCE.customerToDto(customerOptional.get());
      customerDto.setUsername(username);
      return customerDto;
    }
    return null;
  }
}
