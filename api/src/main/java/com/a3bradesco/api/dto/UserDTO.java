package com.a3bradesco.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UserDTO {
    @NotBlank
    @Pattern(regexp = "^([A-Za-zÀ-ÖØ-öø-ÿ]+[-']?\\s+){1,}[A-Za-zÀ-ÖØ-öø-ÿ]+[-']?$", message = "Insira um nome válido")
    private String name;
    @NotBlank
    @Pattern(regexp = "\\d{11}", message = "Insira um CPF válido")
    private String cpf;
    @NotBlank
    @Email(regexp = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-z]+", message = "Insira um e-mail válido")
    private String email;
    @NotBlank
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$", message = "Insira uma senha válida")
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
