package com.bradesco.token.dto;

// Recebimento dos dados
public class VerificacaoDTO {
    private String usuario;
    private String token;
    
    // Getters and setters
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}