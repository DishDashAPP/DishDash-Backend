package com.dish_dash.order.domain.repository;

import com.dish_dash.order.domain.model.OrderItem;
import com.dish_dash.order.domain.model.ShoppingCartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartItemRepository extends JpaRepository<ShoppingCartItem, Long> {
    List<ShoppingCartItem> findByShoppingCartId(long id);
}
