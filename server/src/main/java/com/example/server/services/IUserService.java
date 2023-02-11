package com.example.server.services;

import com.example.server.models.user.User;

import java.util.List;

public interface IUserService {

    List<User> getAllUsers();
    User addUser(User user);

    void deleteUserById(Long id);

    User editUser(Long id, User user);
}
