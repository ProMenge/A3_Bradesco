package com.a3bradesco.api.controllers;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.a3bradesco.api.config.security.CustomUserDetails;
import com.a3bradesco.api.config.security.CustomUserDetailsService;
import com.a3bradesco.api.config.security.jwt.JwtService;
import com.a3bradesco.api.dto.LoginDTO;
import com.a3bradesco.api.dto.UserDTO;
import com.a3bradesco.api.entities.User;
import com.a3bradesco.api.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    JwtService jwtService;

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO dto) {
        try {
            User user = userService.login(dto.getIdentifier(), dto.getPassword());

            String token = jwtService.generateToken(new CustomUserDetails(user, dto.getIdentifier()), user.getId());

            return ResponseEntity.ok(Map.of("token", token, "user", user));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        List<User> userList = userService.findAll();
        return ResponseEntity.ok().body(userList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping
    public ResponseEntity<?> saveNewUser(@RequestBody @Valid UserDTO dto) {
        try {
            User userObj = userService.saveNewUser(dto);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(userObj.getId()).toUri();
            return ResponseEntity.created(uri).body(userObj);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("Usuário deletado com sucesso!");
    }
}