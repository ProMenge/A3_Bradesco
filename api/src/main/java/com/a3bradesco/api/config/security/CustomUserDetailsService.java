package com.a3bradesco.api.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.a3bradesco.api.entities.User;
import com.a3bradesco.api.repositories.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user;
        
        if(username.contains("@")){
            user = userRepository.findByEmail(username);
        } else {
            user = userRepository.findByCpf(username);
        }

        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
        
        System.out.println("USER " + user.getName());
        return new CustomUserDetails(user, username);
    }
}