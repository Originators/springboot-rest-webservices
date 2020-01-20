package com.controller;

import com.entities.Order;
import com.entities.User;
import com.exception.UserNotFoundException;
import com.services.OrderService;
import com.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/users")
public class OrderController {

    private OrderService orderService;
    private UserService userService;

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping("/{userId}/orders")
    public List<Order> getAllOrders(@PathVariable("userId") Long userId) throws UserNotFoundException {
        Optional<User> op = userService.getUserById(userId);
        if(op.isPresent()){
            User user = op.get();
            return orderService.getAllOrders(user);
        } else {
            throw new UserNotFoundException("user is not found");
        }
    }

    @PostMapping(value = "/{userId}/orders")
    public ResponseEntity<Object> createOrder(@RequestBody Order order, @PathVariable("userId") Long userId) throws UserNotFoundException {
        Optional<User> op = userService.getUserById(userId);
        if(op.isPresent()){
            User user = op.get();
            order.setUser(user);
            Long orderId = orderService.createNewOrder(order);
            return new ResponseEntity<>("order with order Id : " + order+ " is created", HttpStatus.CREATED);
        } else {
            throw new UserNotFoundException("user is not found");
        }
    }

    @GetMapping (path= "/{userId}/orders/{orderId}")
    public ResponseEntity<Object> getOrderbyOrderId(@PathVariable Long userId, @PathVariable Long orderId) throws UserNotFoundException {
        Optional<User> op = userService.getUserById(userId);
        if(op.isPresent()){
            User user = op.get();
            Order order = orderService.getOrderByOrderId(orderId);
            return new ResponseEntity<>(order, HttpStatus.OK);
        } else {
            throw new UserNotFoundException("user is not found");
        }
    }
}
