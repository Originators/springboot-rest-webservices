package com.controller.mapstruct;

import com.dtos.UserMapStructDTO;
import com.entities.User;
import com.exception.UserNotFoundException;
import com.mappers.UserMapper;
import com.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

@RestController
@Validated
@RequestMapping(value = "/map-struct/users")
public class UserControllerMapStruct {

    private UserMapper mapper;
    private UserService userService;

    public UserControllerMapStruct(UserMapper mapper, UserService userService) {
        this.mapper = mapper;
        this.userService = userService;
    }

    @GetMapping(path = "/{userId}")
    public UserMapStructDTO getUserById(@Min(1) @PathVariable("userId") long userId) {
        try {
            Optional<User> userOptional = userService.getUserById(userId);
            User user = userOptional.get();
            return mapper.userToUserMapStructDTO(user);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping
    public List<UserMapStructDTO> getAllUsers(){
        return mapper.userToUserMapStructDTO(userService.getAllUsers());
    }

}
