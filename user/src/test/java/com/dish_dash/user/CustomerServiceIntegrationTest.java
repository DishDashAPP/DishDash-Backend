package com.dish_dash.user;

import com.dishDash.common.dto.CustomerDto;
import com.dish_dash.user.adapters.repository.CustomerRepository;
import com.dish_dash.user.domain.model.Customer;
import com.dish_dash.user.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerServiceIntegrationTest {

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private CustomerService customerService;

  private CustomerDto customerDto;

  @BeforeEach
  void setUp() {
    customerDto = CustomerDto.builder()
            .id(1L)
            .firstName("FIRSTNAME")
            .lastName("LASTNAME")
            .address("ADDRESS")
            .phoneNumber("PHONE_NUMBER")
            .build();
  }

  @Test
  void modifyProfile_ShouldReturnTrue_WhenCustomerExists() {
    Customer initialCustomer = Customer.builder()
            .id(1L)
            .firstName("FIRSTNAME")
            .lastName("LASTNAME")
            .address("ADDRESS")
            .phoneNumber("PHONE_NUMBER")
            .build();
    customerRepository.save(initialCustomer);

    Boolean result = customerService.modifyProfile(1L, customerDto);

    Customer modifiedCustomer = customerRepository.findById(1L).orElse(null);

    assertTrue(result);
    assertNotNull(modifiedCustomer);
    assertEquals(customerDto.getFirstName(), modifiedCustomer.getFirstName());
    assertEquals(customerDto.getLastName(), modifiedCustomer.getLastName());
    assertEquals(customerDto.getAddress(), modifiedCustomer.getAddress());
    assertEquals(customerDto.getPhoneNumber(), modifiedCustomer.getPhoneNumber());
  }

  @Test
  void modifyProfile_ShouldReturnFalse_WhenCustomerDoesNotExist() {
    Boolean result = customerService.modifyProfile(2L, customerDto);

    assertFalse(result);
  }

  @Test
  void createCustomer_ShouldSaveCustomer() {
    customerService.createCustomer(customerDto);

    Customer savedCustomer = customerRepository.findById(1L).orElse(null);

    assertNotNull(savedCustomer);
    assertEquals(customerDto.getId(), savedCustomer.getId());
  }

  @Test
  void getCustomerAddress_ShouldReturnAddress_WhenCustomerExists() {
    Customer customer = Customer.builder()
            .id(1L)
            .firstName("FIRSTNAME")
            .lastName("LASTNAME")
            .address("ADDRESS")
            .phoneNumber("PHONE_NUMBER")
            .build();
    customerRepository.save(customer);

    String address = customerService.getCustomerAddress(1L);

    assertEquals("ADDRESS", address);
  }

  @Test
  void getCustomerAddress_ShouldReturnNull_WhenCustomerDoesNotExist() {
    String address = customerService.getCustomerAddress(2L);

    assertNull(address);
  }

  @Test
  void getUserProfile_ShouldReturnCustomerDto_WhenCustomerExists() {
    Customer customer = Customer.builder()
            .id(1L)
            .firstName("FIRSTNAME")
            .lastName("LASTNAME")
            .address("ADDRESS")
            .phoneNumber("PHONE_NUMBER")
            .build();
    customerRepository.save(customer);

    CustomerDto result = customerService.getUserProfile(1L);

    assertNotNull(result);
    assertEquals(customer.getId(), result.getId());
    assertEquals(customer.getFirstName(), result.getFirstName());
    assertEquals(customer.getLastName(), result.getLastName());
    assertEquals(customer.getAddress(), result.getAddress());
    assertEquals(customer.getPhoneNumber(), result.getPhoneNumber());
  }

  @Test
  void getUserProfile_ShouldReturnNull_WhenCustomerDoesNotExist() {
    CustomerDto result = customerService.getUserProfile(2L);

    assertNull(result);
  }
}