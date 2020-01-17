package com.controller;

import com.entities.User;
import com.services.UserService;
import org.springframework.web.bind.annotation.*;

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
    public User createUser(@RequestBody User user) {
       return userService.createUser(user);
    }

    @GetMapping(path = "/get-user/{userId}")
    public User getUserById(@PathVariable("userId") long userId) {
        return userService.getUserById(userId);
    }

    @PutMapping(path= "/update-users/{userId}")
    public User updateUserById(@PathVariable("userId") long userId, @RequestBody User user) {
        return userService.updateUserById(userId, user);
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
