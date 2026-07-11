package com.everythingcrms.controller;

import com.everythingcrms.entity.UserTest;
import com.everythingcrms.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class HelloController {

    private final UserService userService;

    public HelloController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String sayHello() {
        return "Hello World";
    }

    @GetMapping("/users")
    public List<UserTest> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserTest> getUserById(@PathVariable String id){
        return userService.getUserById(id).map(ResponseEntity::ok)
                                        .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("users/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable String id){
        boolean deleted = userService.deleteUserById(id);

        if(deleted){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/users")
    public ResponseEntity<UserTest> addUser(@RequestBody UserTest user){

        if(userService.userExists(user.getId())){
            return ResponseEntity.status(409).build();
        }

        UserTest savedUser = userService.addUser(user);
        return ResponseEntity.status(201).body(savedUser);
    }

    

    @PutMapping("/user/{id}")

    public ResponseEntity<UserTest> updateUser(@RequestBody UserTest UpdatedUser,@PathVariable String id){
        return userService.updateUserById(id,UpdatedUser).map(ResponseEntity :: ok)
        .orElse(ResponseEntity.notFound().build());
    }


}
