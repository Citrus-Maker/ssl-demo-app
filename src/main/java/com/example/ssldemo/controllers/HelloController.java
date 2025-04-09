package com.example.ssldemo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String greet() {
        return "Hello from secure Spring Boot Application";
    }

    @GetMapping("/info")
    public String info() {
        return "Your connection is secured with SSL/TLS";
    }
}
