package com.a3bradesco.api.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.a3bradesco.api.config.security.CustomUserDetails;
import com.a3bradesco.api.config.security.CustomUserDetailsService;
import com.a3bradesco.api.config.security.jwt.JwtService;
import com.a3bradesco.api.dto.UserReportDTO;
import com.a3bradesco.api.entities.User;
import com.a3bradesco.api.entities.UserReport;
import com.a3bradesco.api.entities.enums.ReportType;
import com.a3bradesco.api.services.UserReportService;
import com.a3bradesco.api.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;

@AutoConfigureMockMvc(addFilters = false) // Ignorei a segurança do teste para evitar o erro 403, desativando a
                                          // autenticação e autorização
@WebMvcTest(UserReportController.class)
class UserReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserReportService reportService;

    @MockBean
    private UserService userService;

    @MockBean
        private JwtService jwtService;
        
        @MockBean
private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void ReturnListOfUserReports() throws Exception {
        Long userId = 1L;

        // Garantir que o User tenha o ID configurado corretamente
        User user = new User(userId, "Igor", "49825725868", "igor@gmail.com", "Galinha7");
        UserReport report = new UserReport(1L, user, ReportType.CPF, "12312312312", LocalDateTime.now());

        // Mockando os métodos de serviço
        when(userService.findById(userId)).thenReturn(user);
        when(reportService.findByReporter(user)).thenReturn(Arrays.asList(report));

        // Criando o CustomUserDetails corretamente
        CustomUserDetails customUserDetails = new CustomUserDetails(user, user.getEmail());

        // Mockando a Authentication para retornar o CustomUserDetails
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(customUserDetails);

        // Executando o teste com a autenticação mockada
        mockMvc.perform(get("/users/{userId}/user-reports", userId)
                .principal(authentication))  // Passando a autenticação mockada
                .andExpect(status().isOk());
    }

    @Test
    void CreateNewReport() throws Exception {

        Long userId = 1L;
        User user = new User(userId, "Igor Molina", "49825725868", "igor@gmail.com", "Galinha7");

        UserReportDTO dto = new UserReportDTO();
        dto.setReportType(ReportType.CPF);
        dto.setReportValue("49724125685");

        UserReport report = new UserReport(1L, user, ReportType.CPF, "49724125685", LocalDateTime.now());
        when(reportService.saveNewReport(eq(userId), any(UserReportDTO.class))).thenReturn(report);

        // Executa o teste
        mockMvc.perform(post("/users/" + userId + "/user-reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void DeleteReport() throws Exception {
        Long userId = 1L;
        Long reportId = 2L;

        // Como é void, usamos doNothing
        doNothing().when(reportService).deleteReport(reportId);

        mockMvc.perform(delete("/users/{userId}/user-reports/{reportId}", userId, reportId))
                .andExpect(status().isOk());
    }

    @Test
    void CreateNewReport_ThrowsIllegalArgumentException_ReturnsBadRequest() throws Exception {
        Long userId = 1L;
        UserReportDTO dto = new UserReportDTO();
        dto.setReportType(ReportType.CPF);
        dto.setReportValue("valor-invalido");

        when(reportService.saveNewReport(eq(userId), any(UserReportDTO.class)))
                .thenThrow(new IllegalArgumentException("Usuário não encontrado"));

        mockMvc.perform(post("/users/" + userId + "/user-reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void CreateNewReport_ThrowsIllegalStateException_ReturnsBadRequest() throws Exception {
        Long userId = 1L;
        UserReportDTO dto = new UserReportDTO();
        dto.setReportType(ReportType.CPF);
        dto.setReportValue("valor-duplicado");

        when(reportService.saveNewReport(eq(userId), any(UserReportDTO.class)))
                .thenThrow(new IllegalStateException("Esse report já foi feito"));

        mockMvc.perform(post("/users/" + userId + "/user-reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

}