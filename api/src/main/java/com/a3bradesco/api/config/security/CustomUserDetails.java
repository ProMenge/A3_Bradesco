package com.a3bradesco.api.config.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.a3bradesco.api.entities.User;

public class CustomUserDetails implements UserDetails {

    private final User user;
    private final String login;

    public CustomUserDetails(User user, String login){
        this.user = user;
        this.login = login;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(user.getUserRole().toString()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    public Long getId(){
        return user.getId();
    }

    public String getLogin(){
        return this.login;
    }
}
