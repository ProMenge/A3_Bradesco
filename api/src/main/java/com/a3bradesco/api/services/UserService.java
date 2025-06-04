package com.a3bradesco.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.a3bradesco.api.dto.UserDTO;
import com.a3bradesco.api.entities.User;
import com.a3bradesco.api.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {
    
    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        Optional<User> userObject = userRepository.findById(id);
        return userObject.orElseThrow(() -> 
                new EntityNotFoundException(String.valueOf(id)));
    }

    public User insert(User user){
        return userRepository.save(user);
    }

    public User saveNewUser(UserDTO dto){
        if (userRepository.findByCpf(dto.getCpf()) == null &&
            userRepository.findByEmail(dto.getEmail()) == null) {
            User user = new User();
            user.setName(dto.getName());
            user.setEmail(dto.getEmail());
            user.setCpf(dto.getCpf());
            user.setPassword(passwordEncoder.encode(dto.getPassword()));

            return insert(user);
        }

        throw new IllegalArgumentException("Já existe um usuário cadastrado com essas credenciais");
        
    }

    private boolean isCpf(String cpf){
        return cpf.matches("\\d{11}");
    }

    public User login(String identifier, String password){
        User user;

        if(isCpf(identifier)){
            user = userRepository.findByCpf(identifier);
        } else {
            user = userRepository.findByEmail(identifier);
        }

        if(user == null || !passwordEncoder.matches(password, user.getPassword())){
            throw new IllegalArgumentException("Email, cpf ou senha inválido");
        }

        return user;
    }

    public void deleteById(Long id){
        userRepository.deleteById(id);
    } 

    public void deleteUser(Long id) {
        findById(id);
        deleteById(id);
    }
}
