package com.a3bradesco.api.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

@WebMvcTest(UserReportController.class)
class UserReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserReportService reportService;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void ReturnListOfUserReports() throws Exception {
        UserReport report = new UserReport(1L, new User(), ReportType.CPF, "12312312312");
        when(reportService.findAll()).thenReturn(Arrays.asList(report));

        mockMvc.perform(get("/userreports"))
                .andExpect(status().isOk());
    }

    @Test
    void ReturnReportById() throws Exception {
        UserReport report = new UserReport(1L, new User(), ReportType.CPF, "12312312312");
        when(reportService.findById(1L)).thenReturn(report);

        mockMvc.perform(get("/userreports/1"))
                .andExpect(status().isOk());
    }

    @Test
    void CreateNewReport() throws Exception {
        
        User user = new User(1L, "Igor", "11111111111", "igor@gmail.com", "123");
        UserReportDTO dto = new UserReportDTO();
        dto.setReporterId(user.getId());
        dto.setReportType(ReportType.CPF.ordinal());
        dto.setReportValue("12312312312");

        UserReport report = new UserReport(1L, user, ReportType.CPF, "12312312312");

        when(userService.findById(user.getId())).thenReturn(user);
        when(reportService.insert(any(UserReport.class))).thenReturn(report);

        
        mockMvc.perform(post("/user-reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }
}
