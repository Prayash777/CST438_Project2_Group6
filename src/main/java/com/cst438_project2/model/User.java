package com.cst438_project2.model;

import jakarta.persistence.*;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;


    @Column(name = "username",unique = true, nullable = false)
    private String username;


    @Column(name = "password", nullable = false)
    private String password;


    @Enumerated(EnumType.STRING) 
    private Role role;

    @Column(nullable = false)
    private String email;

    public User() {
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;  
    }

    public void setRole(Role role) {
        this.role = role;  
    }

    public void makeAdmin() {
        this.role = Role.ADMIN; 
    }


    public void removeAdmin() {
        this.role = Role.USER;
    }
}