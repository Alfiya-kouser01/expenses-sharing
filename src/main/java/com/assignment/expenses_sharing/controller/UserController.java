package com.assignment.expenses_sharing.controller;

import com.assignment.expenses_sharing.entities.UserTB;
import com.assignment.expenses_sharing.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public UserTB createUser(@RequestBody UserTB user) {
        log.info("Creating user: {}", user);
        return userService.saveUser(user);
    }

    @GetMapping("/{id}")
    public UserTB getUserById(@PathVariable int id) {
        log.info("Fetching user with ID: {}", id);
        return userService.getUserById(id);
    }

    @GetMapping
    public List<UserTB> getAllUsers() {
        log.info("Fetching all users");
        return userService.fetchAllUsers();
    }

    @PutMapping("/{id}")
    public UserTB updateUser(@PathVariable int id, @RequestBody UserTB user) {
        log.info("Updating user with ID: {}", id);
        return userService.updateUserById(id, user);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable int id) {
        log.info("Deleting user with ID: {}", id);
        return userService.deleteUserById(id);
    }
}
