package com.dish_dash.user.service;

import com.dish_dash.user.domain.DeliveryPerson;
import com.dish_dash.user.domain.DeliveryPersonStatus;
import com.dish_dash.user.repository.DeliveryPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryPersonService {

    @Autowired
    private DeliveryPersonRepository deliveryPersonRepository;

    public DeliveryPerson modifyProfile(DeliveryPerson deliveryPerson) {
        // Assuming deliveryPersonRepository.save() updates the profile if the delivery person already exists
        return deliveryPersonRepository.save(deliveryPerson);
    }

    public boolean createDeliveryPerson(DeliveryPerson deliveryPerson) {
        // Assuming deliveryPersonRepository.save() returns the saved entity
        DeliveryPerson savedPerson = deliveryPersonRepository.save(deliveryPerson);
        return savedPerson != null;
    }

    public DeliveryPerson getUserProfile(String deliveryPersonID) {
        // Assuming deliveryPersonRepository.findById() returns an Optional<DeliveryPerson>
        return deliveryPersonRepository.findById(deliveryPersonID).orElse(null);
    }

    public DeliveryPersonStatus getDeliveryPersonStatus(String deliveryPersonId) {
        // Logic to fetch DeliveryPersonStatus for the given deliveryPersonId
        DeliveryPerson deliveryPerson = deliveryPersonRepository.findById(deliveryPersonId).orElse(null);
        if (deliveryPerson != null) {
            return deliveryPerson.getStatus();
        }
        return null;
    }
}
