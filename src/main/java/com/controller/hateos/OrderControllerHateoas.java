package com.controller.hateos;

import com.entities.Order;
import com.entities.User;
import com.exception.UserNotFoundException;
import com.services.OrderService;
import com.services.UserService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/hateoas/users")
public class OrderControllerHateoas {

    private OrderService orderService;
    private UserService userService;

    public OrderControllerHateoas(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping("/{userId}/orders")
    public CollectionModel<Order> getAllOrders(@PathVariable("userId") Long userId) throws UserNotFoundException {
        Optional<User> op = userService.getUserById(userId);
        if(op.isPresent()){
            User user = op.get();
            //return orderService.getAllOrders(user);
             List<Order> orders = user.getOrders();
             return new CollectionModel<Order>(orders);

        } else {
            throw new UserNotFoundException("user is not found");
        }
    }
}
