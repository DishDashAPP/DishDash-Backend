package com.dish_dash.order.domain.repository;

import com.dishDash.common.enums.OrderStatus;
import com.dish_dash.order.domain.model.OrderItem;
import com.dish_dash.order.domain.model.ShoppingCart;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    Collection<ShoppingCart> findByCustomerId(long customerID);
}
