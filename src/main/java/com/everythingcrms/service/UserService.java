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

    public Optional<UserTest> updateUserById(String id,UserTest UpdatedUser){

        Optional<UserTest> existingUser = getUserById(id);

        if(existingUser.isPresent()){
          UserTest user = existingUser.get();
          
          user.setAge(UpdatedUser.getAge());
          user.setName(UpdatedUser.getName());
          user.setGender(UpdatedUser.getGender());
          user.setId(UpdatedUser.getId());

          return Optional.of(user);


        }

        return Optional.empty();

       
    }

    public boolean userExists(String id){
        return users.stream().anyMatch(user -> user.getId().equals(id));
    }

    public boolean deleteUserById(String id){
        return users.removeIf(user -> user.getId().equals(id));
    }

}
