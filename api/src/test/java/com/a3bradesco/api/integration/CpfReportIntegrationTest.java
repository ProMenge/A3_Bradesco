package com.a3bradesco.api.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@SpringBootTest
@AutoConfigureMockMvc
public class CpfReportIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateAndGetCpfReport() throws Exception {
        String cpf = "12345678901";

        // POST
        mockMvc.perform(post("/cpf-reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"cpf\": \"" + cpf + "\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cpf").value(cpf))
                .andExpect(jsonPath("$.reportQuantity").value(1))
                .andExpect(jsonPath("$.lastTimeReported").value(LocalDate.now().toString()));

        // GET
        mockMvc.perform(get("/cpf-reports/" + cpf))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cpf").value(cpf))
                .andExpect(jsonPath("$.reportQuantity").value(1))
                .andExpect(jsonPath("$.lastTimeReported").value(LocalDate.now().toString()));
    }

    @Test
    void shouldIncreaseDenunciasOnMultiplePosts() throws Exception {
        String cpf = "99988877766";

        // 1º POST
        mockMvc.perform(post("/cpf-reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"cpf\": \"" + cpf + "\"}"))
                .andExpect(status().isCreated());

        // 2º POST
        mockMvc.perform(post("/cpf-reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"cpf\": \"" + cpf + "\"}"))
                .andExpect(status().isCreated());

        // GET e verifica se acumulou 2 denúncias
        mockMvc.perform(get("/cpf-reports/" + cpf))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cpf").value(cpf))
                .andExpect(jsonPath("$.reportQuantity").value(2))
                .andExpect(jsonPath("$.lastTimeReported").value(LocalDate.now().toString()));
    }



@Test
void shouldListAllCpfReportsWithCorrectCounts() throws Exception {
    String[] cpfs = { "11111111111", "22222222222", "33333333333" };

    // 1 denúncia
    mockMvc.perform(post("/cpf-reports")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"cpf\": \"" + cpfs[0] + "\"}"))
            .andExpect(status().isCreated());

    // 2 denúncias
    for (int i = 0; i < 2; i++) {
        mockMvc.perform(post("/cpf-reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"cpf\": \"" + cpfs[1] + "\"}"))
                .andExpect(status().isCreated());
    }

    // 3 denúncias
    for (int i = 0; i < 3; i++) {
        mockMvc.perform(post("/cpf-reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"cpf\": \"" + cpfs[2] + "\"}"))
                .andExpect(status().isCreated());
    }

    // GET e verificação
    // GET e verificação
mockMvc.perform(get("/cpf-reports"))
    .andExpect(status().isOk())
    .andExpect(jsonPath("$[?(@.cpf=='11111111111')].reportQuantity").value(1))
    .andExpect(jsonPath("$[?(@.cpf=='22222222222')].reportQuantity").value(2))
    .andExpect(jsonPath("$[?(@.cpf=='33333333333')].reportQuantity").value(3))
    .andExpect(jsonPath("$[?(@.cpf=='11111111111')].lastTimeReported").value(LocalDate.now().toString()))
    .andExpect(jsonPath("$[?(@.cpf=='22222222222')].lastTimeReported").value(LocalDate.now().toString()))
    .andExpect(jsonPath("$[?(@.cpf=='33333333333')].lastTimeReported").value(LocalDate.now().toString()));


}
@Test
void shouldReturnSpecificCpfReportCorrectly() throws Exception {
    String cpf = "12345678901";

    // Envia uma denúncia
    mockMvc.perform(post("/cpf-reports")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"cpf\": \"" + cpf + "\"}"))
            .andExpect(status().isCreated());

    // Verifica o retorno da denúncia específica
    mockMvc.perform(get("/cpf-reports/" + cpf))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.cpf").value(cpf))
            .andExpect(jsonPath("$.reportQuantity").value(1))
            .andExpect(jsonPath("$.lastTimeReported").value(LocalDate.now().toString()));
}


}