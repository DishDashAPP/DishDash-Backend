package com.dish_dash.user.service;

import com.dishDash.common.dto.DeliveryPersonDto;
import com.dishDash.common.dto.LocationDto;
import com.dishDash.common.enums.DeliveryPersonStatus;
import com.dishDash.common.enums.ErrorCode;
import com.dishDash.common.exception.CustomException;
import com.dishDash.common.feign.authentication.AuthenticationApi;
import com.dish_dash.user.adapters.repository.DeliveryPersonRepository;
import com.dish_dash.user.adapters.repository.LocationRepository;
import com.dish_dash.user.domain.mapper.UserMapper;
import com.dish_dash.user.domain.model.DeliveryPerson;
import com.dish_dash.user.domain.model.Location;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeliveryPersonService {

  private final LocationRepository locationRepository;
  private final DeliveryPersonRepository deliveryPersonRepository;
  private final AuthenticationApi authenticationApi;

  public boolean modifyProfile(long id, DeliveryPersonDto deliveryPersonDto) {
    boolean exists = deliveryPersonRepository.existsById(id);
    if (exists) {
      log.info("Modifying profile for delivery person ID: {}", id);
      deliveryPersonRepository.modify(
          deliveryPersonDto.getFirstName(),
          deliveryPersonDto.getLastName(),
          deliveryPersonDto.getPhoneNumber(),
          id);
    } else {
      log.info("modifyProfile. Delivery person ID: {} not found, creating new profile", id);
      DeliveryPerson newDeliveryPerson = UserMapper.INSTANCE.dtoToDeliveryPerson(deliveryPersonDto);
      newDeliveryPerson.setId(id);
      deliveryPersonRepository.save(newDeliveryPerson);
    }
    return true;
  }

  public void createDeliveryPerson(DeliveryPersonDto deliveryPersonDto) {
    log.info("Creating new delivery person with ID: {}", deliveryPersonDto.getId());
    DeliveryPerson newDeliveryPerson = UserMapper.INSTANCE.dtoToDeliveryPerson(deliveryPersonDto);
    deliveryPersonRepository.save(newDeliveryPerson);
    log.info("Successfully created delivery person with ID: {}", newDeliveryPerson.getId());
  }

  public DeliveryPersonDto getUserProfile(long deliveryPersonId) {
    log.info("Retrieving user profile for delivery person ID: {}", deliveryPersonId);
    String username = authenticationApi.getUsername(deliveryPersonId);
    return deliveryPersonRepository
        .findById(deliveryPersonId)
        .map(
            deliveryPerson -> {
              DeliveryPersonDto deliveryPersonDto =
                  UserMapper.INSTANCE.deliveryPersonToDto(deliveryPerson);
              deliveryPersonDto.setUsername(username);
              return deliveryPersonDto;
            })
        .orElseThrow(
            () -> {
              log.error("getUserProfile. Delivery person ID: {} not found", deliveryPersonId);
              return new CustomException(ErrorCode.NOT_FOUND, "Delivery person not found");
            });
  }

  public DeliveryPersonStatus getDeliveryPersonStatus(long deliveryPersonId) {
    log.info("Getting status for delivery person ID: {}", deliveryPersonId);
    return deliveryPersonRepository
        .findById(deliveryPersonId)
        .map(DeliveryPerson::getStatus)
        .orElseThrow(
            () -> {
              log.error(
                  "getDeliveryPersonStatus. Delivery person ID: {} not found", deliveryPersonId);
              return new CustomException(ErrorCode.NOT_FOUND, "Delivery person not found");
            });
  }

  public boolean setLocation(LocationDto locationDto, long deliveryPersonId) {
    log.info("Setting location for delivery person ID: {}", deliveryPersonId);
    return deliveryPersonRepository
        .findById(deliveryPersonId)
        .map(
            deliveryPerson -> {
              Location location =
                  locationRepository
                      .findByDeliveryID(deliveryPersonId)
                      .orElseGet(
                          () ->
                              locationRepository.save(
                                  Location.builder()
                                      .deliveryID(deliveryPersonId)
                                      .latitude(locationDto.getLatitude())
                                      .longitude(locationDto.getLongitude())
                                      .build()));
              locationRepository.modify(
                  locationDto.getLatitude(), locationDto.getLongitude(), location.getId());

              deliveryPerson.setLocation(location);
              deliveryPersonRepository.save(deliveryPerson);
              log.info("Location updated for delivery person ID: {}", deliveryPersonId);
              return true;
            })
        .orElseThrow(
            () -> {
              log.error("Delivery person ID: {} not found", deliveryPersonId);
              return new CustomException(ErrorCode.NOT_FOUND, "Delivery person not found");
            });
  }

  public LocationDto getLocation(long deliveryPersonId) {
    log.info("Retrieving location for delivery person ID: {}", deliveryPersonId);
    return locationRepository
        .findByDeliveryID(deliveryPersonId)
        .map(UserMapper.INSTANCE::locationToDto)
        .orElseThrow(
            () -> {
              log.error("Location for delivery person ID: {} not found", deliveryPersonId);
              return new CustomException(ErrorCode.NOT_FOUND, "Location not found");
            });
  }

  public long setActiveOrder(long orderId) {
    log.info("Assigning active order ID: {} to a delivery person", orderId);
    return deliveryPersonRepository
        .findFirstByStatus(DeliveryPersonStatus.ACTIVE)
        .map(
            deliveryPerson -> {
              deliveryPerson.setCurrentOrderId(orderId);
              deliveryPerson.setStatus(DeliveryPersonStatus.BUSY);
              deliveryPersonRepository.save(deliveryPerson);
              log.info(
                  "Assigned order ID: {} to delivery person ID: {}",
                  orderId,
                  deliveryPerson.getId());
              return deliveryPerson.getId();
            })
        .orElseThrow(
            () -> {
              log.error("No active delivery person available for order ID: {}", orderId);
              return new CustomException(
                  ErrorCode.SERVICE_UNAVAILABLE, "No active delivery person available");
            });
  }
}
