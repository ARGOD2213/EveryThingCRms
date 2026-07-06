package com.everythingcrms.service;
import org.springframework.stereotype.Service;
import com.everythingcrms.entity.UserTest;

import java.util.*;

@Service
public class UserService {
    private final List<UserTest> users = new ArrayList<>();

    public UserService(){
             users.add(new UserTest(26, "Mahindra", "Male", "123938"));
        users.add(new UserTest(30, "Vyshu", "Female", "378"));
    
    }

    public List<UserTest> getAllUsers(){
        return users;
    }

    public Optional<UserTest> getUserById(String id){
        return users.stream().filter(user -> user.getId().equals(id)).findFirst();
    }

    public UserTest addUser(UserTest user){
        users.add(user);
        return user;
    }

    public boolean deleteUserById(String id){
        return users.removeIf(user -> user.getId().equals(id));
    }

}
