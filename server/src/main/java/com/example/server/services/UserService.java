package com.example.server.services;

import com.example.server.models.user.User;
import com.example.server.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {


    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User addUser(User user) {
        userRepository.findById(user.getId()).map((existingUser) -> {
            existingUser.setPassword(user.getPassword());
            existingUser.setUsername(user.getUsername());
            return userRepository.save(existingUser);
        }).orElseGet(() -> {
            User newUser = new User(user.getUsername(), user.getPassword(), user.getRole());
            return userRepository.save(newUser);
        });
        return userRepository.save(user);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public User editUser(Long id, User user) {
        return userRepository.findById(id)
                .map(employee -> {
                    employee.setUsername(user.getUsername());
                    employee.setPassword(user.getPassword());
                    employee.setRole(user.getRole());
                    return userRepository.save(employee);
                })
                .orElseGet(() -> {
                    user.setId(id);
                    return userRepository.save(user);
                });
    }
}
