package com.a3bradesco.api.controllers;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import com.a3bradesco.api.entities.CellphoneReport;
import com.a3bradesco.api.services.CellphoneReportService;

@AutoConfigureMockMvc(addFilters = false) // Ignorei a segurança do teste para evitar o erro 403, desativando a autenticação e autorização 
@WebMvcTest(CellphoneReportController.class) 
class CellphoneReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CellphoneReportService service;

    private final String validCellphone = "11992078740";
    private final String invalidCellphone = "123";

    @BeforeEach
    void setup() {
    CellphoneReport report = new CellphoneReport(validCellphone, 1, LocalDate.now());

    when(service.saveNewReport(anyString())).thenReturn(report);
    when(service.findById(validCellphone)).thenReturn(report); // NECESSÁRIO PARA O GET
    doNothing().when(service).deleteReport(validCellphone);    // NECESSÁRIO PARA O DELETE
}
    
    // 1. Cellphone com 11 dígitos(com o DDD incluso) - deve retornar Created (201)
    @Test
    void whenValidCellphone_thenReturnsCreated() throws Exception {
        String json = "{ \"cellphone\": \"" + validCellphone + "\" }";

        mockMvc.perform(post("/cellphone-reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }

    // 2. Número com menos de 11 ou mais dígitos - retorna BadRequest
    @Test
    void whenCWithInvalidLength_thenReturnsBadRequest() throws Exception {
        String json = "{ \"cellphone\": \"" + invalidCellphone + "\" }";
        

        mockMvc.perform(post("/cellphone-reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    // 3. Campo Cellphone vazio - retorna BadRequest
    @Test
    void whenCellphoneBlank_thenReturnsBadRequest() throws Exception {
        String json = "{ \"cellphone\": \"\" }";

        mockMvc.perform(post("/cellphone-reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    // 4 - Nome do campo diferente de 'cellphone' - retorna BadRequest
    @Test
    void whenWrongFieldName_thenReturnsBadRequest() throws Exception {
        String json = "{ \"documento\": \"" + validCellphone + "\" }";

        mockMvc.perform(post("/cellphone-reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }
    // 5 - Cellphone com caracteres alfabéticos deve ser inválido - retorna BadRequest 
    @Test
    void whenCellphoneContainsLetters_thenReturnsBadRequest() throws Exception {
    String invalidCellphone = "11T920B87A0"; // inválido

        mockMvc.perform(post("/cellphone-reports")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"cellphone\": \"" + invalidCellphone + "\"}"))
            .andExpect(status().isBadRequest());
}
    // 6 - Cellphone com símbolos como ".", "-"  - retorna BadRequest
    @Test
    void whenCellphoneHasSymbols_thenReturnsBadRequest() throws Exception {
    String cellphoneWithSymbols = "11.992'078-740";

    mockMvc.perform(post("/cellphone-reports")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"cellphone\": \"" + cellphoneWithSymbols + "\"}"))
            .andExpect(status().isBadRequest());
}
     // 7. GET - Busca um celular válido e espera OK
    @Test
    void whenGetValidCellphone_thenReturnsOk() throws Exception {
        mockMvc.perform(get("/cellphone-reports/" + validCellphone))
                .andExpect(status().isOk());
    }

    // 8. DELETE - Remove um celular válido e espera NoContent
    @Test
    void whenDeleteValidCellphone_thenReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/cellphone-reports/" + validCellphone))
                .andExpect(status().isNoContent());
    }
    
}
