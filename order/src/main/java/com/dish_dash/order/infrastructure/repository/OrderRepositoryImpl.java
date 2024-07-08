package com.dish_dash.order.infrastructure.repository;

import com.dish_dash.order.domain.model.Order;
import com.dish_dash.order.domain.repository.OrderRepository;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepositoryImpl implements OrderRepository {
    private final SpringDataOrderRepository springDataOrderRepository;

    public OrderRepositoryImpl(SpringDataOrderRepository springDataOrderRepository) {
        this.springDataOrderRepository = springDataOrderRepository;
    }

    @Override
    public Order findByID(String id) {
        return springDataOrderRepository.findById(id).orElse(null);
    }

    @Override
    public Order createOrder(Order order) {
        return springDataOrderRepository.save(order);
    }

    @Override
    public Order modifyOrder(Order order) {
        return springDataOrderRepository.save(order);
    }
}
