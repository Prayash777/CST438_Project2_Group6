// package com.cst438_project2.repository;
// import org.springframework.stereotype.Repository;
// import org.springframework.data.jpa.repository.JpaRepository;
// import java.util.Optional;
// import com.cst438_project2.model.User;
// import com.cst438_project2.model.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import com.cst438_project2.model.User;
import com.cst438_project2.model.Role;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    Optional<User> findByRole(Role role);
}
