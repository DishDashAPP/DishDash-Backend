package com.dish_dash.user.service;

import com.dishDash.common.dto.DeliveryPersonDto;
import com.dishDash.common.dto.LocationDto;
import com.dishDash.common.enums.DeliveryPersonStatus;
import com.dish_dash.user.adapters.repository.DeliveryPersonRepository;
import com.dish_dash.user.adapters.repository.LocationRepository;
import com.dish_dash.user.domain.mapper.UserMapper;
import com.dish_dash.user.domain.model.DeliveryPerson;
import com.dish_dash.user.domain.model.Location;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryPersonService {

  private final LocationRepository locationRepository;
  private final DeliveryPersonRepository deliveryPersonRepository;

  public Boolean modifyProfile(Long id, DeliveryPersonDto deliveryPersonDto) {
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
        .orElse(false);
  }

  public void createDeliveryPerson(DeliveryPersonDto deliveryPersonDto) {
    deliveryPersonRepository.save(DeliveryPerson.builder().id(deliveryPersonDto.getId()).build());
  }

  public DeliveryPersonDto getUserProfile(Long deliveryPersonId) {
    return deliveryPersonRepository
        .findById(deliveryPersonId)
        .map(UserMapper.INSTANCE::deliveryPersonToDto)
        .orElse(null);
  }

  public DeliveryPersonStatus getDeliveryPersonStatus(Long deliveryPersonId) {
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
                      .orElse(
                          Location.builder()
                              .deliveryID(deliveryPersonId)
                              .latitude(locationDto.getLatitude())
                              .longitude(locationDto.getLongitude())
                              .build());
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
}
