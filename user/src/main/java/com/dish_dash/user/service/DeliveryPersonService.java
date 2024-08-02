package com.dish_dash.user.service;

import com.dishDash.common.dto.DeliveryPersonDto;
import com.dishDash.common.dto.LocationDto;
import com.dishDash.common.enums.DeliveryPersonStatus;
import com.dishDash.common.feign.authentication.AuthenticationApi;
import com.dish_dash.user.adapters.repository.DeliveryPersonRepository;
import com.dish_dash.user.adapters.repository.LocationRepository;
import com.dish_dash.user.domain.mapper.UserMapper;
import com.dish_dash.user.domain.model.DeliveryPerson;
import com.dish_dash.user.domain.model.Location;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryPersonService {

  private final LocationRepository locationRepository;
  private final DeliveryPersonRepository deliveryPersonRepository;
  private final AuthenticationApi authenticationApi;

  public Boolean modifyProfile(long id, DeliveryPersonDto deliveryPersonDto) {
    return deliveryPersonRepository
        .findById(id)
        .map(
            deliveryPerson -> {
              deliveryPersonRepository.modify(
                  deliveryPersonDto.getFirstName(),
                  deliveryPersonDto.getLastName(),
                  deliveryPersonDto.getPhoneNumber(),
                  id);
              return true;
            })
        .orElseGet(
            () -> {
              DeliveryPerson newDeliveryPerson =
                  UserMapper.INSTANCE.dtoToDeliveryPerson(deliveryPersonDto);
              newDeliveryPerson.setId(id);
              deliveryPersonRepository.save(newDeliveryPerson);
              return true;
            });
  }

  public void createDeliveryPerson(DeliveryPersonDto deliveryPersonDto) {
    DeliveryPerson newDeliveryPerson = UserMapper.INSTANCE.dtoToDeliveryPerson(deliveryPersonDto);
    deliveryPersonRepository.save(newDeliveryPerson);
  }

  public DeliveryPersonDto getUserProfile(long deliveryPersonId) {
    String username = authenticationApi.getUsername(deliveryPersonId);
    Optional<DeliveryPerson> deliveryPersonOptional =
        deliveryPersonRepository.findById(deliveryPersonId);
    if (deliveryPersonOptional.isPresent()) {
      DeliveryPersonDto deliveryPersonDto =
          UserMapper.INSTANCE.deliveryPersonToDto(deliveryPersonOptional.get());
      deliveryPersonDto.setUsername(username);
      return deliveryPersonDto;
    }
    return null;
  }

  public DeliveryPersonStatus getDeliveryPersonStatus(long deliveryPersonId) {
    return deliveryPersonRepository
        .findById(deliveryPersonId)
        .map(DeliveryPerson::getStatus)
        .orElse(null);
  }

  public boolean setLocation(LocationDto locationDto, long deliveryPersonId) {
    return deliveryPersonRepository
        .findById(deliveryPersonId)
        .map(
            deliveryPerson -> {
              Location location =
                  locationRepository
                      .findByDeliveryID(deliveryPersonId)
                      .orElseGet(
                          () ->
                              Location.builder()
                                  .deliveryID(deliveryPersonId)
                                  .latitude(locationDto.getLatitude())
                                  .longitude(locationDto.getLongitude())
                                  .build());
              location.setLatitude(locationDto.getLatitude());
              location.setLongitude(locationDto.getLongitude());
              locationRepository.save(location);
              deliveryPerson.setLocation(location);
              deliveryPersonRepository.save(deliveryPerson);
              return true;
            })
        .orElse(false);
  }

  public LocationDto getLocation(long deliveryPersonId) {
    return locationRepository
        .findByDeliveryID(deliveryPersonId)
        .map(UserMapper.INSTANCE::locationToDto)
        .orElse(null);
  }

  public long setActiveOrder(long orderId) {
    return deliveryPersonRepository
        .findFirstByStatus(DeliveryPersonStatus.ACTIVE)
        .map(
            deliveryPerson -> {
              deliveryPerson.setCurrentOrderId(orderId);
              deliveryPerson.setStatus(DeliveryPersonStatus.BUSY);
              deliveryPersonRepository.save(deliveryPerson);
              return deliveryPerson.getId();
            })
        .orElse(0L);
  }
}
