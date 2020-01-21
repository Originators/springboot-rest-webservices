package com.controller.hateos;

import com.entities.Order;
import com.entities.User;
import com.exception.UserNotFoundException;
import com.services.UserService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@Validated
@RequestMapping(value = "/hateoas/users")
public class UserControllerHateoas {

    private UserService userService;

    public UserControllerHateoas(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public CollectionModel<User> getAllUsers() throws UserNotFoundException {
        List<User> allUsers = userService.getAllUsers();
        for(User user : allUsers) {
            Link selfLink = WebMvcLinkBuilder.linkTo(this.getClass()).slash(user.getId()).withSelfRel();
            user.add(selfLink);

            CollectionModel<Order> orders = WebMvcLinkBuilder.methodOn(OrderControllerHateoas.class).getAllOrders(user.getId());
            Link ordersLink = WebMvcLinkBuilder.linkTo(orders).withRel("all-orders");
            user.add(ordersLink);
        }
        Link linkForGetAllUsers = WebMvcLinkBuilder.linkTo(this.getClass()).withSelfRel();

        CollectionModel<User> finalEm = new CollectionModel<>(allUsers, linkForGetAllUsers);
        return finalEm;
    }

    @GetMapping(path = "/{userId}")
    public EntityModel<User> getUserById(@Min(1) @PathVariable("userId") long userId) {
        try {
            User user = userService.getUserById(userId).get();
            Link selfLink = WebMvcLinkBuilder.linkTo(this.getClass()).slash(userId).withSelfRel();
            user.add(selfLink);
            //EntityModel finalResource = new EntityModel(user,selfLink);
            EntityModel finalResource = new EntityModel(user);
            return finalResource;
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
