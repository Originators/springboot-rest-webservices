package com.controller;

import com.entities.User;
import com.exception.UserExistsException;
import com.exception.UserNotFoundException;
import com.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path="/all-users")
    public List<User> getAllUsers(){
        System.out.println("get req");
        return userService.getAllUsers();
    }

    @PostMapping(path = "/create-user")
    public ResponseEntity<User> createUser(@RequestBody User user, UriComponentsBuilder builder) {
        try{
            userService.createUser(user);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(builder.path("/get-user/{userId}").buildAndExpand(user.getId()).toUri());
            return new ResponseEntity<>(user, headers, HttpStatus.CREATED); // body , header, status
        } catch (UserExistsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }

    @GetMapping(path = "/get-user/{userId}")
    public User getUserById(@PathVariable("userId") long userId) {
        try {
            return userService.getUserById(userId);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PutMapping(path= "/update-users/{userId}")
    public User updateUserById(@PathVariable("userId") long userId, @RequestBody User user) {
        try {
            return userService.updateUserById(userId, user);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @DeleteMapping(path = "/delete-user/{userId}")
    public void deleteUserById(@PathVariable("userId") long userId){
        userService.deleteUserById(userId);
    }

    @GetMapping(path = "/users/byusername/{userId}")
    public User getUserById(@PathVariable("userId") String userName) {
        return userService.getUserByUserName(userName);
    }



}
