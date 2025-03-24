package com.cst438_project2.service;

import com.cst438_project2.model.User;
import com.cst438_project2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get user by ID (int or Long—be consistent with your User entity)
    public User getUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }

    // Create new user
    public User createUser(User user) {
        // In real code, you might:
        // 1) check if user.getUsername() already exists
        // 2) hash the password (e.g., BCrypt)
        return userRepository.save(user);
    }

    // Update user details
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    // Delete user by ID
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    // ----------- NEW METHODS for your "UserController" checks ------------

    // Check if username already exists
    public boolean existsByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    // Find user by username
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Simple authentication check (in real code, you'd compare hashed passwords)
    public boolean authenticate(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            return false;
        }
        User user = userOpt.get();
        // In real code, compare hashed password:
        // BCrypt.checkpw(password, user.getPassword());
        return user.getPassword().equals(password);
    }
}
