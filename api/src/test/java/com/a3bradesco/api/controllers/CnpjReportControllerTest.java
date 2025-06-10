package com.a3bradesco.api.controllers;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import com.a3bradesco.api.entities.CnpjReport;
import com.a3bradesco.api.services.CnpjReportService;

@AutoConfigureMockMvc(addFilters = false) // Ignorei a segurança do teste para evitar o erro 403, desativando a autenticação e autorização 
@WebMvcTest(CnpjReportController.class) 
class CnpjReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CnpjReportService service;

    private final String validCnpj = "12345678900123";

    @BeforeEach
    void setup() {
        CnpjReport report = new CnpjReport(validCnpj, 1, LocalDate.now());
        when(service.saveNewReport(anyString())).thenReturn(report);
    }

    // 1. CNPJ com 14 dígitos - deve retornar Created (201)
    @Test
    void whenValidCnpj_thenReturnsCreated() throws Exception {
        String json = "{ \"cnpj\": \"" + validCnpj + "\" }";

        mockMvc.perform(post("/cnpj-reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }

    // 2. CNPJ com menos de 14 ou mais de 14 dígitos - retorna BadRequest
    @Test
    void whenCWithInvalidLength_thenReturnsBadRequest() throws Exception {
        String json = "{ \"cnpj\": \"123\" }";

        mockMvc.perform(post("/cnpj-reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    // 3. Campo CNJP vazio - retorna BadRequest
    @Test
    void whenCnpjBlank_thenReturnsBadRequest() throws Exception {
        String json = "{ \"cnpj\": \"\" }";

        mockMvc.perform(post("/cnpj-reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    // 4 - Nome do campo diferente de 'cnpj' - retorna BadRequest
    @Test
    void whenWrongFieldName_thenReturnsBadRequest() throws Exception {
        String json = "{ \"documento\": \"" + validCnpj + "\" }";

        mockMvc.perform(post("/cnpj-reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }
    // 5 - Cnpj com caracteres alfabéticos deve ser inválido - retorna BadRequest 
    @Test
    void whenCnpjContainsLetters_thenReturnsBadRequest() throws Exception {
    String invalidcnpj = "123ABC7890X123"; // inválido

        mockMvc.perform(post("/cnpj-reports")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"cnpj\": \"" + invalidcnpj + "\"}"))
            .andExpect(status().isBadRequest());
}
    // 6 - Cnpj com símbolos como ".", "-"  - retorna BadRequest
    @Test
    void whenCnpjHasSymbols_thenReturnsBadRequest() throws Exception {
    String cnpjWithSymbols = "123.456.789-00123";

    mockMvc.perform(post("/cnpj-reports")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"cnpj\": \"" + cnpjWithSymbols + "\"}"))
            .andExpect(status().isBadRequest());
}
    // 7. GET - Busca um Cnpj válido e espera OK
    @Test
    void whenGetValidCnpj_thenReturnsOk() throws Exception {
        mockMvc.perform(get("/cnpj-reports/" + validCnpj))
                .andExpect(status().isOk());
    }

    // 8. DELETE - Remove um Cnpj válido 
    @Test
    void whenDeleteValidCnpj_thenReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/cnpj-reports/" + validCnpj))
                .andExpect(status().isOk());
    }
    
    // 9. GET - Retorna lista de Cnpj cadastrados
    @Test
    void whenFindAll_thenReturnsListAndStatusOk() throws Exception {
    CnpjReport report1 = new CnpjReport("12345678900123", 2, LocalDate.now());
    CnpjReport report2 = new CnpjReport("12345678900127", 1, LocalDate.now());
    when(service.findAll()).thenReturn(Arrays.asList(report1, report2));
    mockMvc.perform(get("/cnpj-reports"))
            .andExpect(status().isOk());
}






}
