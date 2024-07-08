package com.dish_dash.order.domain.repository;

import com.dish_dash.order.domain.model.Order;
import com.dish_dash.order.domain.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    Order findByID(String id);

    default Order createOrder(String orderID, String details) {
        Order order = new Order();
        order.setOrderID(orderID);
        order.setDetails(details);
        return save(order);
    }

    default Order modifyOrder(String orderID, OrderItem[] orderItems) {
        Order order = findByID(orderID);
        if (order != null) {
            order.setOrderItems(orderItems);
            return save(order);
        }
        return null;
    }
}
