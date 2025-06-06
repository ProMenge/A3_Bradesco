package com.a3bradesco.api.controllers;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.a3bradesco.api.entities.CpfReport;
import com.a3bradesco.api.services.CpfReportService;

@AutoConfigureMockMvc(addFilters = false) // Ignorei a segurança do teste para evitar o erro 403, desativando a autenticação e autorização  
@WebMvcTest(CpfReportController.class)
class CpfReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CpfReportService service;

    private final String validCpf = "12345678900";

    @BeforeEach
    void setup() {
        CpfReport report = new CpfReport(validCpf, 1, LocalDate.now());
        when(service.saveNewReport(anyString())).thenReturn(report);
    }

    // 1. CPF com 11 dígitos - deve retornar Created (201)
    @Test
    void whenValidCpf_thenReturnsCreated() throws Exception {
        String json = "{ \"cpf\": \"" + validCpf + "\" }";

        mockMvc.perform(post("/cpf-reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }

    // 2. CPF com menos de 11 ou mais de 11 dígitos - retorna BadRequest
    @Test
    void whenCpfWithInvalidLength_thenReturnsBadRequest() throws Exception {
        String json = "{ \"cpf\": \"123\" }";

        mockMvc.perform(post("/cpf-reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    // 3. Campo CPF vazio - retorna BadRequest
    @Test
    void whenCpfBlank_thenReturnsBadRequest() throws Exception {
        String json = "{ \"cpf\": \"\" }";

        mockMvc.perform(post("/cpf-reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    // 4 - Nome do campo diferente de 'cpf' - retorna BadRequest
    @Test
    void whenWrongFieldName_thenReturnsBadRequest() throws Exception {
        String json = "{ \"documento\": \"" + validCpf + "\" }";

        mockMvc.perform(post("/cpf-reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }
    // 5 - CPF com caracteres alfabéticos deve ser inválido - retorna BadRequest 
    @Test
    void whenCpfContainsLetters_thenReturnsBadRequest() throws Exception {
    String invalidCpf = "123ABC7890X"; // inválido

        mockMvc.perform(post("/cpf-reports")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"cpf\": \"" + invalidCpf + "\"}"))
            .andExpect(status().isBadRequest());
}
    // 6 - Cpf com símbolos como ".", "-"  - retorna BadRequest
    @Test
    void whenCpfHasSymbols_thenReturnsBadRequest() throws Exception {
    String cpfWithSymbols = "123.456.789-00";

    mockMvc.perform(post("/cpf-reports")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"cpf\": \"" + cpfWithSymbols + "\"}"))
            .andExpect(status().isBadRequest());
}
   // 7. GET - Busca um celular válido e espera OK
    @Test
    void whenGetValidCpf_thenReturnsOk() throws Exception {
        mockMvc.perform(get("/cpf-reports/" + validCpf))
                .andExpect(status().isOk());
    }

    // 8. DELETE - Remove um celular válido e espera NoContent
    @Test
    void whenDeleteValidCpf_thenReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/cpf-reports/" + validCpf))
                .andExpect(status().isNoContent());
    }
    





}
