package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Cst438Project2Group6Application {

	public static void main(String[] args) {
		SpringApplication.run(Cst438Project2Group6Application.class, args);
	}
	
	@GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
      return String.format("Hello %s!!!", name);
    }
	@GetMapping("/")
	public String home(){
		return String.format("Hi. You are in home rn");
	}
}
