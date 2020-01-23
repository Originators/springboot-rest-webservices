package com.controller.modelmapper;

import com.entities.User;
import com.entities.UserDTO;
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
@Validated
@RequestMapping(value = "/model-mapper/users")
public class UserControllerModelMapper {

    private ModelMapper modelMapper;
    private UserService userService;

    public UserControllerModelMapper(ModelMapper modelMapper, UserService userService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @GetMapping(path = "/{userId}")
    public UserDTO getUserById(@Min(1) @PathVariable("userId") long userId) {
        try {
            Optional<User> userOptional = userService.getUserById(userId);
            User user = userOptional.get();
            return modelMapper.map(user, UserDTO.class);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
