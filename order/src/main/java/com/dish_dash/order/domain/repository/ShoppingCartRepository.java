package com.dish_dash.order.domain.repository;

import com.dish_dash.order.domain.model.ShoppingCart;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
  Optional<ShoppingCart> findByCustomerIdAndRestaurantOwnerId(
      long customerID, long restaurantOwnerId);

  List<ShoppingCart> findByCustomerId(long customerID);
}
