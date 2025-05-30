package com.a3bradesco.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class CnpjDTO {
    @NotBlank
    @Pattern(regexp = "\\d{14}", message = "Cnpj deve conter exatamente 14 dígitos numéricos.")
    private String cnpj;

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}