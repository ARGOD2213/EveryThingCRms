package com.everythingcrms.service;

import com.everythingcrms.entity.UserTest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserServiceTest {

    @Test
    void shouldInitializeWithSeededUsers() {
        UserService service = new UserService();

        List<UserTest> users = service.getAllUsers();

        assertEquals(2, users.size());
        assertEquals("Mahindra", users.get(0).getName());
        assertEquals("Vyshu", users.get(1).getName());
    }

    @Test
    void shouldFindUserByIdWhenPresent() {
        UserService service = new UserService();

        Optional<UserTest> user = service.getUserById("123938");

        assertTrue(user.isPresent());
        assertEquals("Mahindra", user.get().getName());
    }

    @Test
    void shouldReturnEmptyOptionalWhenUserIdIsMissing() {
        UserService service = new UserService();

        Optional<UserTest> user = service.getUserById("missing-id");

        assertFalse(user.isPresent());
    }

    @Test
    void shouldAddUserAndReturnIt() {
        UserService service = new UserService();
        UserTest newUser = new UserTest(35, "Alice", "Female", "999");

        UserTest addedUser = service.addUser(newUser);

        assertEquals(newUser, addedUser);
        assertEquals(3, service.getAllUsers().size());
    }

    @Test
    void shouldDeleteUserById() {
        UserService service = new UserService();

        boolean removed = service.deleteUserById("123938");

        assertTrue(removed);
        assertEquals(1, service.getAllUsers().size());
    }
}
