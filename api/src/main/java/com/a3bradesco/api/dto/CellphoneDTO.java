package com.a3bradesco.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class CellphoneDTO {
    @NotBlank
    @Pattern(regexp = "\\d{11}", message = "Celular deve conter de 10 a 11 dígitos numéricos, incluindo DDD.")
    private String cellphone;

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }
}