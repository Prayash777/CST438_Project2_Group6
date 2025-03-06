package com.cst438_project2.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")

public class User{
    
    @Column(unique = true, nullable = false)
    private String username;
    
    @Column(nullable = false)
    private String password;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Enumerated(EnumType.STRING)
    private Role role;
    
    private String email;

    public User(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
        this.id = 0;
        this.role = Role.USER;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void makeAdmin(){
        this.role = Role.ADMIN;
    }

    public void removeAdmin(){
        this.role = Role.USER;
    }
}