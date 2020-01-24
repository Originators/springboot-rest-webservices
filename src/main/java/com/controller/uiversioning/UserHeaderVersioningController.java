package com.controller.uiversioning;

import com.dtos.UserDTOV1;
import com.dtos.UserDTOV2;
import com.entities.User;
import com.exception.UserNotFoundException;
import com.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.Min;
import java.util.Optional;

@RestController
@RequestMapping(value="/versioning/header/users")
@Validated
public class UserHeaderVersioningController {

    private UserService userService;
    private ModelMapper modelMapper;

    public UserHeaderVersioningController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(path = {"/{userId}"}, headers ="API-VERSION=1")
    public UserDTOV1 getUserByIdV1(@Min(1) @PathVariable("userId") long userId) {
        try {
            Optional<User> userOptional = userService.getUserById(userId);
            User user = userOptional.get();
            return modelMapper.map(user, UserDTOV1.class);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping(path = {"/{userId}"}, headers ="API-VERSION=2")
    public UserDTOV2 getUserByIdV2(@Min(1) @PathVariable("userId") long userId) {
        try {
            Optional<User> userOptional = userService.getUserById(userId);
            User user = userOptional.get();
            return modelMapper.map(user, UserDTOV2.class);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
