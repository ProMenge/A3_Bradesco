package com.a3bradesco.api.controllers;

import com.a3bradesco.api.entities.SiteReport;
import com.a3bradesco.api.services.SiteReportService;

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
@WebMvcTest(SiteReportController.class) 
class SiteReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SiteReportService service;


    private final String validUrl = "https://github.com";

    @BeforeEach
    void setup() {
        SiteReport report = new SiteReport(validUrl, 1, LocalDate.now());
        when(service.saveNewReport(anyString())).thenReturn(report);
    }
    // 1. https e .com - retorna Created
    @Test
    void whenValidUrlWithHttps_thenReturnsCreated() throws Exception {
        String json = "{ \"site\": \"https://github.com\" }";

        mockMvc.perform(post("/site-reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }
    // 2. Sem https - retorna Created
    @Test
    void whenValidUrlWithoutHttps_thenReturnsCreated() throws Exception {
        String json = "{ \"site\": \"github.com\" }";

        mockMvc.perform(post("/site-reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }
    // 3. Com www e .com - retorna Created
    @Test
    void whenValidUrlWithWww_thenReturnsCreated() throws Exception {
        String json = "{ \"site\": \"www.github.com\" }";

        mockMvc.perform(post("/site-reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }
    // 4. Com www e .com.br - retorna Created
    @Test
    void whenValidUrlWithComBr_thenReturnsCreated() throws Exception {
        String json = "{ \"site\": \"www.github.com.br\" }";

        mockMvc.perform(post("/site-reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }
    // 5. Sites invalidos - retorna BadRequest 
    @Test
    void whenInvalidDomainSuffix_thenReturnsBadRequest() throws Exception {
        String json1 = "{ \"site\": \"githubcom\" }";
        String json2 = "{ \"site\": \"github .com\" }";
        String json3 = "{ \"site\": \"github..com\" }";

        mockMvc.perform(post("/site-reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json1))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post("/site-reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json2))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post("/site-reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json3))
                .andExpect(status().isBadRequest());        
    }
    // 6. Vazio - Retorna BadResquest
    @Test
    void whenSiteIsBlank_thenReturnsBadRequest() throws Exception {
        String json = "{ \"site\": \"\" }"; 

        mockMvc.perform(post("/site-reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }
    // 7. Fazer o post com o nome "documento" ao invés de "email" - retorna BadResquest
    @Test
    void whenWrongFieldName_thenReturnsBadRequest() throws Exception {
        String json = "{ \"documento\": \"" + validUrl + "\" }";

        mockMvc.perform(post("/site-reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest()); 
    }
}
