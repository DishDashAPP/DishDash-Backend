package com.dish_dash.user.service;

import com.dishDash.common.dto.DeliveryPersonDto;
import com.dishDash.common.dto.LocationDto;
import com.dishDash.common.enums.DeliveryPersonStatus;
import com.dish_dash.user.adapters.repository.DeliveryPersonRepository;
import com.dish_dash.user.adapters.repository.LocationRepository;
import com.dish_dash.user.domain.mapper.UserMapper;
import com.dish_dash.user.domain.model.DeliveryPerson;
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
              UserMapper.INSTANCE.updateDeliveryPersonFromDto(deliveryPersonDto, deliveryPerson);
              deliveryPersonRepository.save(deliveryPerson);
              return true;
            })
        .orElse(false);
  }

  public void createDeliveryPerson(DeliveryPersonDto deliveryPersonDto) {
    deliveryPersonRepository.save(UserMapper.INSTANCE.dtoToDeliveryPerson(deliveryPersonDto));
  }

  public DeliveryPerson getUserProfile(Long deliveryPersonId) {
    return deliveryPersonRepository.findById(deliveryPersonId).orElse(null);
  }

  public DeliveryPersonStatus getDeliveryPersonStatus(Long deliveryPersonId) {
    return deliveryPersonRepository
        .findById(deliveryPersonId)
        .map(DeliveryPerson::getStatus)
        .orElse(null);
  }

  public boolean setLocation(LocationDto locationDto, long deliveryPersonId) {
    return locationRepository
        .findById(locationDto.getId())
        .map(
            location -> {
              UserMapper.INSTANCE.updateLocationFromDto(locationDto, location);
              locationRepository.save(location);
              return true;
            })
        .orElse(false);
  }

  public LocationDto getLocation(long deliveryPersonId) {
    return locationRepository
        .findById(deliveryPersonId)
        .map(UserMapper.INSTANCE::locationToDto)
        .orElse(null);
  }
}
