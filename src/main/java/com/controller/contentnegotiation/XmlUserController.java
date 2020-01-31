package com.controller.contentnegotiation;

import com.entities.User;
import com.exception.UserExistsException;
import com.exception.UserNameNotFoundException;
import com.exception.UserNotFoundException;
import com.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

@RestController
@Validated
@RequestMapping(value = "/xml/users")
public class XmlUserController {

    private UserService userService;

    public XmlUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers(){
        System.out.println("get req");
        return userService.getAllUsers();
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user, UriComponentsBuilder builder) {
        try{
            userService.createUser(user);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(builder.path("/get-user/{userId}").buildAndExpand(user.getId()).toUri());
            return new ResponseEntity<>(user, headers, HttpStatus.CREATED); // body , header, status
        } catch (UserExistsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }

}
