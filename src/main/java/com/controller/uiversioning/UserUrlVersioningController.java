package com.controller.uiversioning;

import com.dtos.UserDTOV1;
import com.dtos.UserDTOV2;
import com.dtos.UserModelMapperDTO;
import com.entities.User;
import com.exception.UserNotFoundException;
import com.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.Min;
import java.util.Optional;

@RestController
@RequestMapping(value="/versioning/uri/users")
@Validated
public class UserUrlVersioningController {

    private UserService userService;
    private ModelMapper modelMapper;

    public UserUrlVersioningController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(path = {"/v1/{userId}", "/v1.1/{userId}"})
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

    @GetMapping(path = {"/v2/{userId}", "/v2.1/{userId}"})
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
