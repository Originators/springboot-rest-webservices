package com.controller;

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
@RequestMapping(value = "/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
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

    @GetMapping(path = "/{userId}")
    public Optional<User> getUserById( @Min(1) @PathVariable("userId") long userId) {
        try {
            return userService.getUserById(userId);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PutMapping(path= "/{userId}")
    public User updateUserById(@PathVariable("userId") long userId, @RequestBody User user) {
        try {
            return userService.updateUserById(userId, user);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @DeleteMapping(path = "/{userId}")
    public void deleteUserById(@PathVariable("userId") long userId){
        userService.deleteUserById(userId);
    }

    @GetMapping(path = "/byusername/{userName}")
    public User getUserByUserName(@PathVariable("userName") String userName) throws UserNameNotFoundException {
        User user = userService.getUserByUserName(userName);
        if(user == null) {
            throw new UserNameNotFoundException("user with username : " + userName + " is not found.");
        }
        return user;
    }

    @GetMapping("/exception-demo")
    public void throwExceptionDemo(){
        System.out.println("exception demo");
        throw new NullPointerException();
    }



}
