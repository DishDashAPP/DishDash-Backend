package com.dish_dash.order.application.service;

import com.dish_dash.order.domain.model.Rate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RateService {

    @Autowired
    private com.dish_dash.order.adapters.repository.RateRepository rateRepository;

    public boolean setOrderRate(String customerID, String orderID, int point) {
        Rate rate = new Rate();
        rate.setCustomerID(customerID);
        rate.setOrderID(orderID);
        rate.setPoint(point);
        return rateRepository.save(rate) != null;
    }

    public boolean setDeliveryRate(String customerID, String orderID, int point) {
        Rate rate = new Rate();
        rate.setCustomerID(customerID);
        rate.setOrderID(orderID);
        rate.setPoint(point);
        return rateRepository.save(rate) != null;
    }

    public Rate getDeliveryRate(String deliveryPersonID) {
        return rateRepository.findByDeliveryPersonID(deliveryPersonID);
    }
}
