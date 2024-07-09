package com.dish_dash.order.domain.repository;

import com.dish_dash.order.domain.model.Order;
import com.dish_dash.order.domain.model.OrderItem;
import com.dish_dash.order.domain.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    Order findByID(String id);

    default Order createOrder(Order order) {
        return save(order);
    }

    default Order modifyOrder(String orderID, List<OrderItem> orderItems) {
        Order order = findByID(orderID);
        if (order != null) {
            order.setOrderItems(orderItems);
            return save(order);
        }
        return null;
    }

    List<Order> findOrderHistoryByRestaurantOwnerID(String restaurantOwnerID);

    List<Order> findActiveOrdersByRestaurantOwnerID(String restaurantOwnerID);

    List<Order> findByCustomerID(String customerID);

    Order findCurrentOrderByCustomerID(String customerID);

    Order findCurrentOrderByDeliveryPersonID(String deliveryPersonID);
}
