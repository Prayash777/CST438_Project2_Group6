package com.cst438_project2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@SpringBootApplication
@ComponentScan(basePackages = "com.cst438_project2")
public class Cst438Project2Group6Application {

    public static void main(String[] args) {
        SpringApplication.run(Cst438Project2Group6Application.class, args);
    }

    // @GetMapping("/hello")
    // public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
    //     return String.format("Hello %s!!!", name);
    // }

    // @GetMapping("/")
    // public String home() {
    //     return "Hi. You are in home rn";
    // }
}