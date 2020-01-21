package com.controller;

import com.bean.UserDetails;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestController
public class HelloWorldController {

    private ResourceBundleMessageSource resourceBundleMessageSource;

    public HelloWorldController(ResourceBundleMessageSource resourceBundleMessageSource){
        this.resourceBundleMessageSource = resourceBundleMessageSource;
    }

    @RequestMapping(method =  RequestMethod.GET, path = "/hello-world")
    public String helloWorldReqMapping() {
        return "hello world req mapping";
    }

    @GetMapping(path= "/hello-world-req-mapping")
    public String helloWorldGetMapping() {
        return "hello world get mapping";
    }

    @GetMapping(path= "/hello-world-req-mapping-bean")
    public List<UserDetails> userDetails() {
        System.out.println("hello");
        List<UserDetails> userDetails = new ArrayList<>();
        UserDetails userDetails1 = new UserDetails("firstName", "lastName", "bhopal");
        userDetails.add(userDetails1);
        return userDetails;
    }

    @GetMapping(path="/hello-world/i18n")
    public String sayHelloI18N(@RequestHeader(name="Accept-Language", required = false) String locale) {
        return resourceBundleMessageSource.getMessage("hello-world-msg", null, new Locale(locale));
    }

    @GetMapping(path="/hello-world/i18n-2")
    public String sayHelloI18N() {
        return resourceBundleMessageSource.getMessage("hello-world-msg", null, LocaleContextHolder.getLocale());
    }

}
