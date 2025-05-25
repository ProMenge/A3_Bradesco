package com.bradesco.token.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {
    
    // Endpoint
    @GetMapping
    public ResponseEntity<String> home() {
        return ResponseEntity.ok(
            "SafeCall Token API\n\n" +
            "Endpoints disponíveis:\n" +
            "- POST /token - Para gerar um token para um usuário\n" +
            "- POST /token/verificar - Para verificar um token\n" +
            "- GET /token - Para verificar o status da API\n\n" +
            "Use ferramentas como Postman ou cURL para testar os endpoints POST."
        );
    }
}