package com.a3bradesco.api.controllers;

import com.a3bradesco.api.entities.EmailReport;
import com.a3bradesco.api.services.EmailReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false) // Ignorei a segurança do teste para evitar o erro 403, desativando a autenticação e autorização
@WebMvcTest(EmailReportController.class)
class EmailReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmailReportService service;

    private final String validEmail = "caue@gmail.com";

    @BeforeEach
    void setup() {
        EmailReport report = new EmailReport(validEmail, 1, LocalDate.now());
        when(service.saveNewReport(anyString())).thenReturn(report);
    }
    //  1. Email válido - deve retornar Created (201)
    @Test
    void whenValidEmail_thenReturnsCreated() throws Exception {
        String json = "{ \"email\": \"" + validEmail + "\" }";

        mockMvc.perform(post("/email-reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }
    // 2. Email sem @ - retorna BadRequest
    @Test
    void whenOnlyAtSymbol_thenReturnsBadRequest() throws Exception {
        String json = "{ \"email\": \"cauegmail.com\" }";

        mockMvc.perform(post("/email-reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }
    // 3. Dominíos invalidos - retorna BadRequest 
    @Test
    void whenInvalidDomainSuffix_thenReturnsBadRequest() throws Exception {
        String json1 = "{ \"email\": \"caue@gmail.a\" }";
        String json2 = "{ \"email\": \"caue@gmail.\" }";
        String json3 = "{ \"email\": \"caue@gmail\" }";

        mockMvc.perform(post("/email-reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json1))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post("/email-reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json2))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post("/email-reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json3))
                .andExpect(status().isBadRequest());        
    }
    // 4. Dominío sem o "." - retorna BadRequest
    @Test
    void whenMissingDotInDomain_thenReturnsBadRequest() throws Exception {
        String json = "{ \"email\": \"caue@gmailcom\" }";

        mockMvc.perform(post("/email-reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }
    // 5. Vazio - Retorna BadResquest
    @Test
    void whenEmailIsBlank_thenReturnsBadRequest() throws Exception {
        String json = "{ \"email\": \"\" }";

        mockMvc.perform(post("/email-reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }
    // 6. Fazer o post com o nome "documento" ao invés de "email" - retorna BadResquest
    @Test 
    void whenWrongFieldName_thenReturnsBadRequest() throws Exception {
        String json = "{ \"documento\": \"" + validEmail + "\" }";

        mockMvc.perform(post("/email-reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }
}
