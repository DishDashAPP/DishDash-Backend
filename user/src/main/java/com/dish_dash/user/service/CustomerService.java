package com.dish_dash.user.service;

import com.dishDash.common.dto.CustomerDto;
import com.dishDash.common.enums.ErrorCode;
import com.dishDash.common.exception.CustomException;
import com.dishDash.common.feign.authentication.AuthenticationApi;
import com.dish_dash.user.adapters.repository.CustomerRepository;
import com.dish_dash.user.domain.mapper.UserMapper;
import com.dish_dash.user.domain.model.Customer;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

  private final CustomerRepository customerRepository;
  private final AuthenticationApi authenticationApi;

  public Boolean modifyProfile(long id, CustomerDto customerDto) {
    Optional<Customer> customerOptional = customerRepository.findById(id);

    if (customerOptional.isPresent()) {
      log.info("Modifying profile for customer ID: {}", id);
      Customer customer = customerOptional.get();
      customerRepository.modify(
          customerDto.getFirstName(),
          customerDto.getLastName(),
          customerDto.getAddress(),
          customerDto.getPhoneNumber(),
          customer.getId());
      log.info("Profile modified successfully for customer ID: {}", id);
      return true;
    } else {
      log.info("Customer ID: {} not found, creating new customer", id);
      Customer newCustomer = UserMapper.INSTANCE.dtoToCustomer(customerDto);
      newCustomer.setId(id);
      customerRepository.save(newCustomer);
      log.info("New customer created with ID: {}", id);
      return true;
    }
  }

  public void createCustomer(CustomerDto customerDto) {
    log.info("Creating a new customer with id: {}", customerDto.getId());
    Customer newCustomer = UserMapper.INSTANCE.dtoToCustomer(customerDto);
    customerRepository.save(newCustomer);
    log.info("Successfully created customer with ID: {}", newCustomer.getId());
  }

  public String getCustomerAddress(long customerID) {
    log.info("Retrieving address for customer ID: {}", customerID);
    return customerRepository
        .findById(customerID)
        .map(Customer::getAddress)
        .orElseThrow(
            () -> {
              log.error("Customer ID: {} not found", customerID);
              return new CustomException(ErrorCode.NOT_FOUND, "Customer not found");
            });
  }

  public CustomerDto getUserProfile(long customerId) {
    log.info("Retrieving user profile for customer ID: {}", customerId);
    String username = authenticationApi.getUsername(customerId);
    return customerRepository
        .findById(customerId)
        .map(
            customer -> {
              CustomerDto customerDto = UserMapper.INSTANCE.customerToDto(customer);
              customerDto.setUsername(username);
              return customerDto;
            })
        .orElseThrow(
            () -> {
              log.error("Customer ID: {} not found", customerId);
              return new CustomException(ErrorCode.NOT_FOUND, "Customer not found");
            });
  }
}
