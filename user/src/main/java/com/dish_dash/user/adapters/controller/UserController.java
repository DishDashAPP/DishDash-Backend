package com.dish_dash.user.adapters.controller;

import com.dish_dash.user.domain.model.DeliveryPersonStatus;
import com.dish_dash.user.domain.model.RestaurantOwner;
import com.dish_dash.user.service.CustomerService;
import com.dish_dash.user.service.DeliveryPersonService;
import com.dish_dash.user.service.RestaurantOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private DeliveryPersonService deliveryPersonService;

    @Autowired
    private RestaurantOwnerService restaurantOwnerService;

    @PutMapping("/customer")
    public Customer modifyCustomerProfile(@RequestBody Customer customer) {
        return customerService.modifyProfile(customer);
    }

    @GetMapping("/customer/{id}")
    public Customer getUserProfile(@PathVariable String id) {
        return customerService.getUserProfile(id);
    }

    @PutMapping("/restaurantOwner")
    public RestaurantOwner modifyRestaurantProfile(@RequestBody RestaurantOwner restaurantOwner) {
        return restaurantOwnerService.modifyProfile(restaurantOwner);
    }

    @PutMapping("/deliveryPerson")
    public DeliveryPerson modifyDeliveryPersonProfile(@RequestBody DeliveryPerson deliveryPerson) {
        return deliveryPersonService.modifyProfile(deliveryPerson);
    }

    @PostMapping("/customer")
    public boolean createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    @PostMapping("/deliveryPerson")
    public boolean createDeliveryPerson(@RequestBody DeliveryPerson deliveryPerson) {
        return deliveryPersonService.createDeliveryPerson(deliveryPerson);
    }

    @PostMapping("/restaurantOwner")
    public boolean createRestaurantOwner(@RequestBody RestaurantOwner restaurantOwner) {
        return restaurantOwnerService.createRestaurantOwner(restaurantOwner);
    }

    @GetMapping("/deliveryPerson/status/{id}")
    public DeliveryPersonStatus getDeliveryPersonStatus(@PathVariable String id) {
        return deliveryPersonService.getDeliveryPersonStatus(id);
    }
}
