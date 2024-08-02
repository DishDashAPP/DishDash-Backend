package com.dish_dash.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import com.dishDash.common.dto.RestaurantOwnerDto;
import com.dishDash.common.enums.ErrorCode;
import com.dishDash.common.exception.CustomException;
import com.dishDash.common.feign.authentication.AuthenticationApi;
import com.dishDash.common.feign.order.RateApi;
import com.dish_dash.user.adapters.repository.RestaurantOwnerRepository;
import com.dish_dash.user.domain.model.RestaurantOwner;
import com.dish_dash.user.service.RestaurantOwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class RestaurantOwnerServiceIntegrationTest {

  @Autowired private RestaurantOwnerRepository restaurantOwnerRepository;

  @Autowired private RestaurantOwnerService restaurantOwnerService;

  @MockBean private AuthenticationApi authenticationApi;

  @MockBean private RateApi rateApi;

  private RestaurantOwnerDto restaurantOwnerDto;

  @BeforeEach
  void setUp() {
    restaurantOwnerRepository.deleteAll();

    restaurantOwnerDto =
        RestaurantOwnerDto.builder()
            .id(1L)
            .firstName("FIRSTNAME")
            .lastName("LASTNAME")
            .address("ADDRESS")
            .restaurantName("RESTAURANT_NAME")
            .username("USERNAME")
            .phoneNumber("PHONE_NUMBER")
            .build();
  }

  @Test
  void modifyProfile_ShouldReturnTrue_WhenRestaurantOwnerExists() {
    RestaurantOwner initialOwner =
        RestaurantOwner.builder()
            .id(1L)
            .firstName("OLD_FIRSTNAME")
            .lastName("OLD_LASTNAME")
            .address("OLD_ADDRESS")
            .restaurantName("OLD_RESTAURANT_NAME")
            .phoneNumber("OLD_PHONE_NUMBER")
            .build();
    restaurantOwnerRepository.save(initialOwner);

    boolean result = restaurantOwnerService.modifyProfile(1L, restaurantOwnerDto);

    RestaurantOwner modifiedOwner = restaurantOwnerRepository.findById(1L).orElse(null);

    assertTrue(result, "Expected profile modification to return true");
    assertNotNull(modifiedOwner, "Modified owner should not be null");
    assertEquals(
        "FIRSTNAME", modifiedOwner.getFirstName(), "First name should be updated to FIRSTNAME");
    assertEquals(
        "LASTNAME", modifiedOwner.getLastName(), "Last name should be updated to LASTNAME");
    assertEquals("ADDRESS", modifiedOwner.getAddress(), "Address should be updated to ADDRESS");
    assertEquals(
        "PHONE_NUMBER",
        modifiedOwner.getPhoneNumber(),
        "Phone number should be updated to PHONE_NUMBER");
    assertEquals(
        "RESTAURANT_NAME",
        modifiedOwner.getRestaurantName(),
        "RESTAURANT_NAME should be updated to RESTAURANT_NAME");
  }

  @Test
  void modifyProfile_ShouldReturnTrue_WhenRestaurantOwnerDoesNotExist() {
    boolean result = restaurantOwnerService.modifyProfile(2L, restaurantOwnerDto);

    RestaurantOwner newOwner = restaurantOwnerRepository.findById(2L).orElse(null);

    assertTrue(result, "Expected profile creation to return true");
    assertNotNull(newOwner, "New owner should not be null");
    assertEquals("FIRSTNAME", newOwner.getFirstName(), "First name should be set to FIRSTNAME");
    assertEquals("LASTNAME", newOwner.getLastName(), "Last name should be set to LASTNAME");
    assertEquals("ADDRESS", newOwner.getAddress(), "Address should be set to ADDRESS");
    assertEquals(
        "PHONE_NUMBER", newOwner.getPhoneNumber(), "Phone number should be set to PHONE_NUMBER");
    assertEquals(
        "RESTAURANT_NAME",
        newOwner.getRestaurantName(),
        "RESTAURANT_NAME should be set to RESTAURANT_NAME");
  }

  @Test
  void createRestaurantOwner_ShouldSaveRestaurantOwner() {
    restaurantOwnerService.createRestaurantOwner(restaurantOwnerDto);

    RestaurantOwner savedOwner = restaurantOwnerRepository.findById(1L).orElse(null);

    assertNotNull(savedOwner, "Saved owner should not be null");
    assertEquals(1L, savedOwner.getId(), "Owner ID should be 1");
    assertEquals("FIRSTNAME", savedOwner.getFirstName(), "First name should be FIRSTNAME");
    assertEquals("LASTNAME", savedOwner.getLastName(), "Last name should be LASTNAME");
    assertEquals("ADDRESS", savedOwner.getAddress(), "Address should be ADDRESS");
    assertEquals(
        "PHONE_NUMBER", savedOwner.getPhoneNumber(), "Phone number should be PHONE_NUMBER");
    assertEquals(
        "RESTAURANT_NAME",
        savedOwner.getRestaurantName(),
        "RESTAURANT_NAME should be RESTAURANT_NAME");
  }

  @Test
  void getUserProfile_ShouldReturnRestaurantOwnerDto_WhenOwnerExists() {
    RestaurantOwner owner =
        RestaurantOwner.builder()
            .id(1L)
            .firstName("FIRSTNAME")
            .lastName("LASTNAME")
            .address("ADDRESS")
            .restaurantName("RESTAURANT_NAME")
            .phoneNumber("PHONE_NUMBER")
            .build();
    restaurantOwnerRepository.save(owner);

    Mockito.when(authenticationApi.getUsername(any())).thenReturn("USERNAME");
    Mockito.when(rateApi.getRestaurantComments(anyLong())).thenReturn(null);

    RestaurantOwnerDto result = restaurantOwnerService.getUserProfile(1L);

    assertNotNull(result, "Profile should not be null");
    assertEquals(1L, result.getId(), "Owner ID should match");
    assertEquals("FIRSTNAME", result.getFirstName(), "First name should be FIRSTNAME");
    assertEquals("LASTNAME", result.getLastName(), "Last name should be LASTNAME");
    assertEquals("ADDRESS", result.getAddress(), "Address should be ADDRESS");
    assertEquals(
        "RESTAURANT_NAME", result.getRestaurantName(), "restaurantName should be RESTAURANT_NAME");
    assertEquals("PHONE_NUMBER", result.getPhoneNumber(), "Phone number should be PHONE_NUMBER");
    assertEquals("USERNAME", result.getUsername(), "Username should be USERNAME");
  }

  @Test
  void getUserProfile_ShouldThrowCustomException_WhenOwnerDoesNotExist() {
    Mockito.when(authenticationApi.getUsername(any())).thenReturn("USERNAME");

    CustomException exception =
        assertThrows(
            CustomException.class,
            () -> {
              restaurantOwnerService.getUserProfile(2L);
            });

    assertEquals(ErrorCode.NOT_FOUND, exception.getErrorCode(), "Expected ErrorCode.NOT_FOUND");
    assertEquals(
        "Restaurant owner not found", exception.getMessage(), "Exception message should match");
  }
}
