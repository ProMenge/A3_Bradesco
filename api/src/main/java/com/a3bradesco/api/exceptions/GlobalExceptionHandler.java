package com.a3bradesco.api.exceptions;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleNotFound(EntityNotFoundException e){
        return ResponseEntity.status(404).body(
            "Recurso com o código " + e.getMessage() + " não encontrado." + 
            "\nFaça uma nova requisição.");
    }

    //Validação dos DTOs: pega todos os campos que não passaram na validação do Pattern
    //e retorna em lista o campo e a defaultMessage definida na validação.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(err -> err.getField() + ": " + err.getDefaultMessage())
            .toList();

        return ResponseEntity.badRequest().body(errors);
    }
    /*Exemplo de retorno:
     * [
            "email: Insira um e-mail válido",
            "cpf: Cpf deve conter exatamente 11 dígitos numéricos.",
            "password: Insira uma senha válida"
        ]
     */
}
