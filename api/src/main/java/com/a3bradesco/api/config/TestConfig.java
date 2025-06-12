package com.a3bradesco.api.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.a3bradesco.api.entities.User;
import com.a3bradesco.api.entities.enums.UserRole;
import com.a3bradesco.api.repositories.UserRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        User user1 = new User(null, "Igor", "11111111111", "igor@gmail.com",
                passwordEncoder.encode("Galinha1"), UserRole.ROLE_USER);
        User user2 = new User(null, "Fred", "22222222222", "fred@gmail.com",
                passwordEncoder.encode("Galinha1"), UserRole.ROLE_USER);
        User user3 = new User(null, "Caue", "33333333333", "caue@gmail.com", passwordEncoder.encode("Galinha1"), UserRole.ROLE_USER);
        User user4 = new User(null, "System admin", "44444444444", "admin@gmail.com", passwordEncoder.encode("Galinha1"), UserRole.ROLE_ADMIN);
        
        userRepository.saveAll(Arrays.asList(user1, user2, user3, user4));
    }
}
