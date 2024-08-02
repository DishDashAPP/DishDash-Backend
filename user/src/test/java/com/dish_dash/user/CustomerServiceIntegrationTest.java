package com.dish_dash.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import com.dishDash.common.dto.CustomerDto;
import com.dishDash.common.exception.CustomException;
import com.dishDash.common.feign.authentication.AuthenticationApi;
import com.dish_dash.user.adapters.repository.CustomerRepository;
import com.dish_dash.user.domain.model.Customer;
import com.dish_dash.user.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class CustomerServiceIntegrationTest {

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private CustomerService customerService;

  @MockBean
  private AuthenticationApi authenticationApi;

  private CustomerDto customerDto;

  @BeforeEach
  void setUp() {
    // Clean up the repository before each test to ensure isolation
    customerRepository.deleteAll();

    customerDto = CustomerDto.builder()
            .id(1L)
            .firstName("FIRSTNAME")
            .lastName("LASTNAME")
            .address("ADDRESS")
            .username("USERNAME")
            .phoneNumber("PHONE_NUMBER")
            .build();
  }

  @Test
  void modifyProfile_ShouldReturnTrue_WhenCustomerExists() {
    Customer initialCustomer = Customer.builder()
            .id(1L)
            .firstName("OLD_FIRSTNAME")
            .lastName("OLD_LASTNAME")
            .address("OLD_ADDRESS")
            .phoneNumber("OLD_PHONE_NUMBER")
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
  void createCustomer_ShouldSaveCustomer() {
    customerService.createCustomer(customerDto);

    Customer savedCustomer = customerRepository.findById(1L).orElse(null);

    assertNotNull(savedCustomer);
    assertEquals(customerDto.getId(), savedCustomer.getId());
    assertEquals(customerDto.getFirstName(), savedCustomer.getFirstName());
    assertEquals(customerDto.getLastName(), savedCustomer.getLastName());
    assertEquals(customerDto.getAddress(), savedCustomer.getAddress());
    assertEquals(customerDto.getPhoneNumber(), savedCustomer.getPhoneNumber());
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
  void getUserProfile_ShouldReturnCustomerDto_WhenCustomerExists() {
    Customer customer = Customer.builder()
            .id(1L)
            .firstName("FIRSTNAME")
            .lastName("LASTNAME")
            .address("ADDRESS")
            .phoneNumber("PHONE_NUMBER")
            .build();
    customerRepository.save(customer);
    Mockito.when(authenticationApi.getUsername(any())).thenReturn("USERNAME");

    CustomerDto result = customerService.getUserProfile(1L);

    assertNotNull(result);
    assertEquals(customer.getId(), result.getId());
    assertEquals(customer.getFirstName(), result.getFirstName());
    assertEquals(customer.getLastName(), result.getLastName());
    assertEquals(customer.getAddress(), result.getAddress());
    assertEquals(customer.getPhoneNumber(), result.getPhoneNumber());
  }

  @Test
  void getUserProfile_ShouldThrowCustomException_WhenCustomerDoesNotExist() {
    Mockito.when(authenticationApi.getUsername(any())).thenReturn("USERNAME");

    Exception exception = assertThrows(CustomException.class, () -> {
      customerService.getUserProfile(2L);
    });

    assertEquals("Customer not found", exception.getMessage());
  }
}
