package com.dish_dash.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import com.dishDash.common.dto.DeliveryPersonDto;
import com.dishDash.common.dto.LocationDto;
import com.dishDash.common.enums.DeliveryPersonStatus;
import com.dishDash.common.exception.CustomException;
import com.dishDash.common.feign.authentication.AuthenticationApi;
import com.dish_dash.user.adapters.repository.DeliveryPersonRepository;
import com.dish_dash.user.adapters.repository.LocationRepository;
import com.dish_dash.user.domain.model.DeliveryPerson;
import com.dish_dash.user.domain.model.Location;
import com.dish_dash.user.service.DeliveryPersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class DeliveryPersonServiceIntegrationTest {

  @Autowired private DeliveryPersonRepository deliveryPersonRepository;

  @Autowired private LocationRepository locationRepository;

  @Autowired private DeliveryPersonService deliveryPersonService;

  @MockBean private AuthenticationApi authenticationApi;

  private DeliveryPersonDto deliveryPersonDto;
  private LocationDto locationDto;

  @BeforeEach
  void setUp() {
    deliveryPersonRepository.deleteAll();
    locationRepository.deleteAll();

    deliveryPersonDto =
        DeliveryPersonDto.builder()
            .id(1L)
            .firstName("FIRSTNAME")
            .lastName("LASTNAME")
            .username("USERNAME")
            .phoneNumber("PHONE_NUMBER")
            .build();

    locationDto = LocationDto.builder().latitude(12.345678F).longitude(98.765432F).build();
  }

  @Test
  void modifyProfile_ShouldReturnTrue_WhenDeliveryPersonExists() {
    DeliveryPerson initialDeliveryPerson =
        DeliveryPerson.builder()
            .id(1L)
            .firstName("OLD_FIRSTNAME")
            .lastName("OLD_LASTNAME")
            .phoneNumber("OLD_PHONE_NUMBER")
            .build();
    deliveryPersonRepository.save(initialDeliveryPerson);

    boolean result = deliveryPersonService.modifyProfile(1L, deliveryPersonDto);

    DeliveryPerson modifiedDeliveryPerson = deliveryPersonRepository.findById(1L).orElse(null);

    assertTrue(result);
    assertNotNull(modifiedDeliveryPerson);
    assertEquals("FIRSTNAME", modifiedDeliveryPerson.getFirstName());
    assertEquals("LASTNAME", modifiedDeliveryPerson.getLastName());
    assertEquals("PHONE_NUMBER", modifiedDeliveryPerson.getPhoneNumber());
  }

  @Test
  void modifyProfile_ShouldReturnFalse_WhenDeliveryPersonDoesNotExist() {
    boolean result = deliveryPersonService.modifyProfile(2L, deliveryPersonDto);

    DeliveryPerson newDeliveryPerson = deliveryPersonRepository.findById(2L).orElse(null);

    assertTrue(result);
    assertNotNull(newDeliveryPerson);
    assertEquals("FIRSTNAME", newDeliveryPerson.getFirstName());
    assertEquals("LASTNAME", newDeliveryPerson.getLastName());
    assertEquals("PHONE_NUMBER", newDeliveryPerson.getPhoneNumber());
  }

  @Test
  void createDeliveryPerson_ShouldSaveDeliveryPerson() {
    deliveryPersonService.createDeliveryPerson(deliveryPersonDto);

    DeliveryPerson savedDeliveryPerson = deliveryPersonRepository.findById(1L).orElse(null);

    assertNotNull(savedDeliveryPerson);
    assertEquals(deliveryPersonDto.getId(), savedDeliveryPerson.getId());
    assertEquals(deliveryPersonDto.getFirstName(), savedDeliveryPerson.getFirstName());
    assertEquals(deliveryPersonDto.getLastName(), savedDeliveryPerson.getLastName());
    assertEquals(deliveryPersonDto.getPhoneNumber(), savedDeliveryPerson.getPhoneNumber());
  }

  @Test
  void getUserProfile_ShouldReturnDeliveryPersonDto_WhenDeliveryPersonExists() {
    DeliveryPerson deliveryPerson =
        DeliveryPerson.builder()
            .id(1L)
            .firstName("FIRSTNAME")
            .lastName("LASTNAME")
            .phoneNumber("PHONE_NUMBER")
            .build();
    deliveryPersonRepository.save(deliveryPerson);

    Mockito.when(authenticationApi.getUsername(any())).thenReturn("USERNAME");
    DeliveryPersonDto result = deliveryPersonService.getUserProfile(1L);

    assertNotNull(result);
    assertEquals(deliveryPerson.getId(), result.getId());
    assertEquals(deliveryPerson.getFirstName(), result.getFirstName());
    assertEquals(deliveryPerson.getLastName(), result.getLastName());
    assertEquals(deliveryPerson.getPhoneNumber(), result.getPhoneNumber());
    assertEquals("USERNAME", result.getUsername());
  }

  @Test
  void getUserProfile_ShouldThrowCustomException_WhenDeliveryPersonDoesNotExist() {
    Mockito.when(authenticationApi.getUsername(any())).thenReturn("USERNAME");

    Exception exception =
        assertThrows(CustomException.class, () -> deliveryPersonService.getUserProfile(2L));

    assertEquals("Delivery person not found", exception.getMessage());
  }

  @Test
  void getDeliveryPersonStatus_ShouldReturnStatus_WhenDeliveryPersonExists() {
    DeliveryPerson deliveryPerson =
        DeliveryPerson.builder().id(1L).status(DeliveryPersonStatus.ACTIVE).build();
    deliveryPersonRepository.save(deliveryPerson);

    DeliveryPersonStatus status = deliveryPersonService.getDeliveryPersonStatus(1L);

    assertEquals(DeliveryPersonStatus.ACTIVE, status);
  }

  @Test
  void getDeliveryPersonStatus_ShouldThrowCustomException_WhenDeliveryPersonDoesNotExist() {
    Exception exception =
        assertThrows(
            CustomException.class, () -> deliveryPersonService.getDeliveryPersonStatus(2L));

    assertEquals("Delivery person not found", exception.getMessage());
  }

  @Test
  void setLocation_ShouldReturnTrue_WhenDeliveryPersonExists() {
    DeliveryPerson deliveryPerson =
        deliveryPersonRepository.save(
            DeliveryPerson.builder().id(1L).status(DeliveryPersonStatus.ACTIVE).build());

    boolean result = deliveryPersonService.setLocation(locationDto, deliveryPerson.getId());

    Location location = locationRepository.findByDeliveryID(deliveryPerson.getId()).orElse(null);

    assertTrue(result);
    assertNotNull(location);
    assertEquals(locationDto.getLatitude(), location.getLatitude());
    assertEquals(locationDto.getLongitude(), location.getLongitude());
  }

  @Test
  void setLocation_ShouldThrowCustomException_WhenDeliveryPersonDoesNotExist() {
    Exception exception =
        assertThrows(
            CustomException.class, () -> deliveryPersonService.setLocation(locationDto, 2L));

    assertEquals("Delivery person not found", exception.getMessage());
  }

  @Test
  void getLocation_ShouldReturnLocationDto_WhenDeliveryPersonExists() {
    Location location =
        Location.builder()
            .deliveryID(1L)
            .latitude((long) 12.345678)
            .longitude((long) 98.765432)
            .build();
    locationRepository.save(location);

    LocationDto result = deliveryPersonService.getLocation(1L);

    assertNotNull(result);
    assertEquals(location.getLatitude(), result.getLatitude());
    assertEquals(location.getLongitude(), result.getLongitude());
  }

  @Test
  void getLocation_ShouldThrowCustomException_WhenLocationDoesNotExist() {
    Exception exception =
        assertThrows(CustomException.class, () -> deliveryPersonService.getLocation(2L));

    assertEquals("Location not found", exception.getMessage());
  }

  @Test
  void setActiveOrder_ShouldSetOrderToActiveDeliveryPersonAndReturnId() {
    DeliveryPerson deliveryPerson =
        DeliveryPerson.builder().id(1L).status(DeliveryPersonStatus.ACTIVE).build();
    deliveryPersonRepository.save(deliveryPerson);

    long orderId = 100L;
    Long assignedDeliveryPersonId = deliveryPersonService.setActiveOrder(orderId);

    DeliveryPerson updatedDeliveryPerson =
        deliveryPersonRepository.findById(assignedDeliveryPersonId).orElse(null);

    assertNotNull(assignedDeliveryPersonId);
    assertNotNull(updatedDeliveryPerson);
    assertEquals(1L, assignedDeliveryPersonId);
    assertEquals(orderId, updatedDeliveryPerson.getCurrentOrderId());
    assertEquals(DeliveryPersonStatus.BUSY, updatedDeliveryPerson.getStatus());
  }

  @Test
  void setActiveOrder_ShouldThrowCustomException_WhenNoActiveDeliveryPersonIsAvailable() {
    long orderId = 100L;

    Exception exception =
        assertThrows(CustomException.class, () -> deliveryPersonService.setActiveOrder(orderId));

    assertEquals("No active delivery person available", exception.getMessage());
  }
}
