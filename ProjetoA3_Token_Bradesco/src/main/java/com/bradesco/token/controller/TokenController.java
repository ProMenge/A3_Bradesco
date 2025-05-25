package com.bradesco.token.controller;

import com.bradesco.token.dto.VerificacaoDTO;
import com.bradesco.token.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/token") // Tudo que for requisitado precisa ter o /token no inicio instanciado 
public class TokenController {

    @Autowired
    private TokenService tokenService;
     // Verificando se a api está funcionando corretamente
    @GetMapping
    public ResponseEntity<String> status() {
        return ResponseEntity.ok("API de Token funcionando! Use métodos POST para gerar e verificar tokens.");
    }
    // Gera um token para o usuário
    @PostMapping
    public ResponseEntity<String> gerarToken(@RequestBody String usuario) {
        String token = tokenService.gerarToken(usuario);
        return ResponseEntity.ok("Token gerado: " + token);
    } 
    // Verifica se o token é válido 
    @PostMapping("/verificar")
    public ResponseEntity<String> verificarToken(@RequestBody VerificacaoDTO dto) {
        boolean valido = tokenService.verificarToken(dto.getUsuario(), dto.getToken());
        return ResponseEntity.ok(valido ? "Token válido" : "Token inválido ou expirado");
    }
}