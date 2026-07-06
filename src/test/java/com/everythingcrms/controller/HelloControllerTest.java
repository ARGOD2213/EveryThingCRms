package com.everythingcrms.controller;

import com.everythingcrms.entity.UserTest;
import com.everythingcrms.service.UserService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class HelloControllerTest {

    @Test
    void sayHelloReturnsExpectedGreeting() {
        UserService userService = new UserService();
        HelloController controller = new HelloController(userService);

        String response = controller.sayHello();

        assertEquals("Hello World", response);
    }

    @Test
    void getUsersReturnsUsersFromService() {
        UserService userService = new UserService();
        HelloController controller = new HelloController(userService);

        List<UserTest> users = controller.getUsers();

        assertNotNull(users);
        assertEquals(2, users.size());
        assertEquals("Mahindra", users.get(0).getName());
    }
}
