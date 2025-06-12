package com.a3bradesco.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class SiteDTO {
    // Aceita sites com e sem https://, com e sem www., e sites com
    // um ou mais domínios (apenas .com ou .com.br)
    @NotBlank
    @Pattern(regexp = "^(https?:\\/\\/)?(www\\.)?[a-zA-Z0-9\\-]+(\\.[a-zA-Z0-9\\-]+)+(/.*)?$", message = "Insira um site válido")
    private String site;

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

}