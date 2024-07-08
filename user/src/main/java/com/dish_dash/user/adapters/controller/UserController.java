package com.dish_dash.user.controller;

import com.dish_dash.user.domain.Customer;
import com.dish_dash.user.domain.DeliveryPerson;
import com.dish_dash.user.domain.Restaurant;
import com.dish_dash.user.domain.RestaurantOwner;
import com.dish_dash.user.domain.DeliveryPersonStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/modifyCustomerProfile")
    public Customer modifyCustomerProfile(@RequestBody Customer customer) {
        return userService.modifyCustomerProfile(customer);
    }

    @GetMapping("/getUserProfile/{userID}")
    public Customer getUserProfile(@PathVariable String userID) {
        return userService.getUserProfile(userID);
    }

    @PutMapping("/modifyRestaurantProfile")
    public Restaurant modifyRestaurantProfile(@RequestBody Restaurant restaurant) {
        return userService.modifyRestaurantProfile(restaurant);
    }

    @PutMapping("/modifyDeliveryPersonProfile")
    public DeliveryPerson modifyDeliveryPersonProfile(@RequestBody DeliveryPerson deliveryPerson) {
        return userService.modifyDeliveryPersonProfile(deliveryPerson);
    }

    @PostMapping("/createCustomer")
    public boolean createCustomer(@RequestBody Customer customer) {
        return userService.createCustomer(customer);
    }

    @PostMapping("/createDeliveryPerson")
    public boolean createDeliveryPerson(@RequestBody DeliveryPerson deliveryPerson) {
        return userService.createDeliveryPerson(deliveryPerson);
    }

    @PostMapping("/createRestaurantOwner")
    public boolean createRestaurantOwner(@RequestBody RestaurantOwner restaurantOwner) {
        return userService.createRestaurantOwner(restaurantOwner);
    }

    @GetMapping("/getDeliveryPersonStatus/{deliveryPersonID}")
    public DeliveryPersonStatus getDeliveryPersonStatus(@PathVariable String deliveryPersonID) {
        return userService.getDeliveryPersonStatus(deliveryPersonID);
    }
}
