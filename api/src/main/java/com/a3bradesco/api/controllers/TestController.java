package com.a3bradesco.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @PostMapping("/users")
    public ResponseEntity<String> publicPost() {
        return ResponseEntity.ok("Public POST OK");
    }

    @PostMapping("/users/login")
    public ResponseEntity<String> login() {
        return ResponseEntity.ok("Login OK");
    }

    @GetMapping("/private")
    public ResponseEntity<String> privateEndpoint() {
        return ResponseEntity.ok("Protected OK");
    }
}
