package com.controller.dynamicfiltering;

import com.entities.User;
import com.exception.UserNotFoundException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.Min;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@Validated
@RequestMapping(value = "/jacksonfilter/users")
public class UserMappingJacksonController {

    private UserService userService;

    public UserMappingJacksonController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/{userId}")
    public MappingJacksonValue getUserById(@Min(1) @PathVariable("userId") long userId) {
        try {
            Optional<User> userOptional =  userService.getUserById(userId);
            User user = userOptional.get();


            Set<String> propertiesToShow = new HashSet<>();
            propertiesToShow.add("id");
            propertiesToShow.add("userName");
            propertiesToShow.add("ssn");
            propertiesToShow.add("orders");
            FilterProvider filter = new SimpleFilterProvider()
                    .addFilter("propertiesToDisplay",
                            SimpleBeanPropertyFilter.filterOutAllExcept(propertiesToShow));

            MappingJacksonValue mapper = new MappingJacksonValue(user);
            mapper.setFilters(filter);

            return mapper;
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
