package com.everythingcrms.controller;

import com.everythingcrms.entity.UserTest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HelloController {

    @GetMapping("/")
    public String sayHell0(){
        return "Hello World";
    }

    @GetMapping("/users")
    public List<UserTest> getUsers(){
        List<UserTest> list = new ArrayList<>();
        list.add(new UserTest(26,"Mahindra","Male","123938"));
        list.add(new UserTest(30,"Vyshu","Female","378"));
        return list;

    }
}
