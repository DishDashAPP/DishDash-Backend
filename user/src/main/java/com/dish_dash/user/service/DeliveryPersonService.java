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

    public Boolean modifyProfile(long id, DeliveryPersonDto deliveryPersonDto) {
        // Check if the delivery person exists, if not, create a new one
        return deliveryPersonRepository
                .findById(id)
                .map(
                        deliveryPerson -> {
                            // Modify existing delivery person details
                            deliveryPersonRepository.modify(
                                    deliveryPersonDto.getFirstName(),
                                    deliveryPersonDto.getLastName(),
                                    deliveryPersonDto.getPhoneNumber(),
                                    id);
                            return true;
                        })
                .orElseGet(() -> {
                    // Insert new delivery person if not found
                    DeliveryPerson newDeliveryPerson = UserMapper.INSTANCE.dtoToDeliveryPerson(deliveryPersonDto);
                    newDeliveryPerson.setId(id); // Ensure the ID from the argument is set
                    deliveryPersonRepository.save(newDeliveryPerson);
                    return true;
                });
    }

    public void createDeliveryPerson(DeliveryPersonDto deliveryPersonDto) {
        // Create a new delivery person from DeliveryPersonDto
        DeliveryPerson newDeliveryPerson = UserMapper.INSTANCE.dtoToDeliveryPerson(deliveryPersonDto);
        deliveryPersonRepository.save(newDeliveryPerson);
    }

    public DeliveryPersonDto getUserProfile(long deliveryPersonId) {
        // Get delivery person profile
        return deliveryPersonRepository
                .findById(deliveryPersonId)
                .map(UserMapper.INSTANCE::deliveryPersonToDto)
                .orElse(null);
    }

    public DeliveryPersonStatus getDeliveryPersonStatus(long deliveryPersonId) {
        // Get delivery person status
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
                            // Find existing location or create a new one
                            Location location =
                                    locationRepository
                                            .findByDeliveryID(deliveryPersonId)
                                            .orElseGet(() -> Location.builder()
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
        // Get location details of the delivery person
        return locationRepository
                .findByDeliveryID(deliveryPersonId)
                .map(UserMapper.INSTANCE::locationToDto)
                .orElse(null);
    }

    public long setActiveOrder(long orderId) {
        // Find the first available delivery person who is not busy and assign the order
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
