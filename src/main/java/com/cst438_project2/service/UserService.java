package com.cst438_project2.service;

import com.cst438_project2.model.User;
import com.cst438_project2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

   
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get user by ID
    public User getUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }

    // Create new user
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // Update user details
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    // Delete user
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }
}