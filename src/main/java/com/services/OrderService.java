package com.services;

import com.entities.Order;
import com.entities.User;
import com.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders(User user){
        return orderRepository.findByUser(user);
    }

    public Long createNewOrder(Order order) {
        return orderRepository.save(order).getOrderId();
    }

    public Order getOrderByOrderId(Long orderId) {
        Optional<Order> op = orderRepository.findById(orderId);
        if(op.isPresent()) {
            return op.get();
        }
        return null;

    }
}
