package com.bradesco.token.service;

import com.bradesco.token.model.Token;
import com.bradesco.token.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class TokenService {

    @Autowired
    private TokenRepository tokenRepository;  // Conexão com o banco de dados

    public String gerarToken(String usuario) {
        String token = String.format("%06d", new Random().nextInt(999999));
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime expira = agora.plusMinutes(5);
        Token novoToken = new Token(token, usuario, agora, expira); 
        tokenRepository.save(novoToken); // Salva no banco
        return token;
    }

    public boolean verificarToken(String usuario, String token) {
        Optional<Token> opt = tokenRepository.findByUsuarioAndToken(usuario, token);
        if (opt.isPresent()) {
            Token encontrado = opt.get();
            return LocalDateTime.now().isBefore(encontrado.getExpiraEm()); // Confere para ver se não está expirado 
        }
        return false;
    }
}