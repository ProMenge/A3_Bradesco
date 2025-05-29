package com.a3bradesco.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a3bradesco.api.entities.User;
import com.a3bradesco.api.repositories.UserRepository;

@Service
public class UserService {
    
    @Autowired
    UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        Optional<User> userObject = userRepository.findById(id);
        return userObject.orElse(null);
    }

    public User insert(User user){
        return userRepository.save(user);
    }

    public void deleteById(Long id){
        userRepository.deleteById(id);
    } 

    public boolean deleteUser(Long id) {
        deleteById(id);
        return findById(id) == null;
    }
}
