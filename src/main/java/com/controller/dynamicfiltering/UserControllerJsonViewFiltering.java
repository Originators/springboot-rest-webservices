package com.controller.dynamicfiltering;

import com.entities.User;
import com.entities.Views;
import com.exception.UserNotFoundException;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.Min;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@Validated
@RequestMapping(value = "/jsonView/users")
public class UserControllerJsonViewFiltering {

    private UserService userService;

    public UserControllerJsonViewFiltering(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/internal/{userId}")
    @JsonView(Views.Internal.class)
    public Optional<User> getUserByIdInternal(@Min(1) @PathVariable("userId") long userId) {
        try {
            Optional<User> userOptional =  userService.getUserById(userId);
            return userOptional;
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping(path = "/external/{userId}")
    @JsonView(Views.External.class)
    public Optional<User> getUserByIdExternal(@Min(1) @PathVariable("userId") long userId) {
        try {
            Optional<User> userOptional =  userService.getUserById(userId);
            return userOptional;
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

}
