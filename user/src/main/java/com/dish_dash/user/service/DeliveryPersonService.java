package com.dish_dash.user.service;

import com.dish_dash.user.domain.model.DeliveryPerson;
import com.dish_dash.user.domain.model.DeliveryPersonStatus;
import com.dish_dash.user.adapters.repository.DeliveryPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryPersonService {

    @Autowired
    private DeliveryPersonRepository deliveryPersonRepository;

    public DeliveryPerson modifyProfile(DeliveryPerson deliveryPerson) {
        return deliveryPersonRepository.save(deliveryPerson);
    }

    public boolean createDeliveryPerson(DeliveryPerson deliveryPerson) {
        DeliveryPerson savedDeliveryPerson = deliveryPersonRepository.save(deliveryPerson);
        return savedDeliveryPerson != null;
    }

    public DeliveryPerson getUserProfile(String deliveryPersonID) {
        return deliveryPersonRepository.findById(deliveryPersonID).orElse(null);
    }

    public DeliveryPersonStatus getDeliveryPersonStatus(String deliveryPersonID) {
        DeliveryPerson deliveryPerson = deliveryPersonRepository.findById(deliveryPersonID).orElse(null);
        if (deliveryPerson != null) {
            return deliveryPerson.getStatus();
        }
        return null;
    }
}
