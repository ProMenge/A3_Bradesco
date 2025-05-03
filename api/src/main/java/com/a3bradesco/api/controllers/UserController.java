package com.a3bradesco.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.a3bradesco.api.entities.User;

@RestController
@RequestMapping("/users")
public class UserController {
    
    @GetMapping
    public ResponseEntity<User> findAll() {
        User u = new User("Igor", "49056714856",  "11970228098", "igor@gmail.com", "123");
        return ResponseEntity.ok().body(u);
    }
}
