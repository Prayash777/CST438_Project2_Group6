package com.cst438_project2;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = "com.cst438_project2")
@EnableJpaRepositories(basePackages = "com.cst438_project2.repository")
@EntityScan(basePackages = "com.cst438_project2.model")
public class JpaConfig {
    // Any additional JPA configuration can be added here if necessary.
}
