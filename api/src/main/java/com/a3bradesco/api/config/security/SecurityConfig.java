package com.a3bradesco.api.config.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.a3bradesco.api.config.security.jwt.JwtAuthFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

     @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        
        config.setAllowedOrigins(List.of("http://localhost:5173"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true); // necessário se estiver usando cookies ou Authorization headers

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
    
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private JwtAuthFilter jwtAuthFilter;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors()
            .and()
            .headers().frameOptions().disable()
            .and()
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, "/users/login", "/users").permitAll()
                
                .requestMatchers(HttpMethod.GET, "/users/*/user-reports").authenticated()
                .requestMatchers(HttpMethod.POST, "/users/*/user-reports").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/users/*/user-reports/*").authenticated()

                .requestMatchers("/users/**", "/cpf-reports/**", "/cnpj-reports/**", "/email-reports/**", "/site-reports/**", "/cellphone-reports/**").hasRole("ADMIN")

                .anyRequest().denyAll()
            
            ).addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
