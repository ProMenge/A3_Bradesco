package com.bradesco.token.repository;

import com.bradesco.token.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// Conexão com o banco de dados
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByUsuarioAndToken(String usuario, String token);
} // Busca o token pelo usuário