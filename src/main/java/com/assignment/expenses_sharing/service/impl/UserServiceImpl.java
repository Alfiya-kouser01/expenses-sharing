package com.assignment.expenses_sharing.service.impl;

import com.assignment.expenses_sharing.entities.UserTB;
import com.assignment.expenses_sharing.repository.UserRepository;
import com.assignment.expenses_sharing.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserTB saveUser(UserTB user) {
        log.info("Saving user: {}", user);
        return userRepository.save(user);
    }

    @Override
    public List<UserTB> fetchAllUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    @Override
    public UserTB getUserById(int id) {
        log.info("Fetching user with ID: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID " + id));
    }

    @Override
    public UserTB updateUserById(int id, UserTB user) {
        log.info("Updating user with ID: {}", id);
        UserTB existingUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID " + id));
        existingUser.setName(user.getName());
        existingUser.setContact(user.getContact());
        existingUser.setEmail(user.getEmail());
        return userRepository.save(existingUser);
    }

    @Override
    public String deleteUserById(int id) {
        log.info("Deleting user with ID: {}", id);
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return "User deleted successfully";
        } else {
            return "No such User present";
        }
    }
}
