package com.assignment.expenses_sharing.service;

import com.assignment.expenses_sharing.entities.UserTB;

import java.util.List;

public interface UserService {
    UserTB saveUser(UserTB user);

    List<UserTB> fetchAllUsers();

    UserTB getUserById(int id);

    UserTB updateUserById(int id, UserTB user);

    String deleteUserById(int id);
}
