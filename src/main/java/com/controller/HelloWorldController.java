package com.controller;

import com.bean.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HelloWorldController {

    @RequestMapping(method =  RequestMethod.GET, path = "/hello-world")
    public String helloWorldReqMapping() {
        return "hello world req mapping";
    }

    @GetMapping(path= "/hello-world-req-mapping")
    public String helloWorldGetMapping() {
        return "hello world get mapping";
    }

    @GetMapping(path= "/hello-world-req-mapping-bean")
    public List<UserDetails> userDetails(){
        List<UserDetails> userDetails = new ArrayList<>();
        UserDetails userDetails1 = new UserDetails("firstName", "lastName", "bhopal");
        userDetails.add(userDetails1);
        return userDetails;
    }

}
