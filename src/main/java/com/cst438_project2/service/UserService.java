package com.cst438_project2.service;

import com.cst438_project2.model.Role;
import com.cst438_project2.model.User;
import com.cst438_project2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    public User updateUser(int id, User updatedUser) {
        return userRepository.findById(id).map(user -> {
            user.setUserame(updatedUser.getUsername());
            user.setEmail(updatedUser.getEmail());
            user.setPassword(updatedUser.getPassword());
            user.setRole(updatedUser.getRole()); // Ensure role updates if needed
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    public User assignAdminRole(int id) {
        return userRepository.findById(id).map(user -> {
            user.makeAdmin();
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }
}