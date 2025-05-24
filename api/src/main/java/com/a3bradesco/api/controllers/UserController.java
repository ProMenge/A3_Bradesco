package com.a3bradesco.api.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.a3bradesco.api.entities.User;
import com.a3bradesco.api.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;
    
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

    @PostMapping()
    public ResponseEntity<User> post(@RequestBody User userObj) {
        userObj = userService.insert(userObj);

        //Devolve no header o location (url) onde pode-se encontrar o usuário criado através de get
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				  .buildAndExpand(userObj.getId()).toUri();

		return ResponseEntity.created(uri).body(userObj);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReport(@PathVariable Long id){
        userService.deleteById(id);
        User isDeleted = userService.findById(id);
        if(isDeleted == null){
            return ResponseEntity.ok("Usuário deletado com sucesso!");
        } else {
            return ResponseEntity.badRequest().build();
        }
        
    }
    
}
