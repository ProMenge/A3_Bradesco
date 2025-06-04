package com.a3bradesco.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class CpfDTO {
    @NotBlank
    @Pattern(regexp = "\\d{11}", message = "Cpf deve conter exatamente 11 dígitos numéricos.")
    private String cpf;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}