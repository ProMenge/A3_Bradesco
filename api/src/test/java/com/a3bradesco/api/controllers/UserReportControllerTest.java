package com.a3bradesco.api.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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

import java.time.LocalDateTime;
import java.util.Arrays;

@WebMvcTest(UserReportController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserReportService reportService;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String BASE_URL = "/users/1/user-reports";

    @Test
    void ReturnListOfUserReports() throws Exception {
        User user = new User(1L, "Igor", "11111111111", "igor@gmail.com", "123");
        UserReport report = new UserReport(1L, user, ReportType.CPF, "12312312312", LocalDateTime.now());

        when(userService.findById(1L)).thenReturn(user);
        when(reportService.findByReporter(user)).thenReturn(Arrays.asList(report));

        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk());
    }

    @Test
void CreateNewReport() throws Exception {
    User user = new User(1L, "Igor", "11111111111", "igor@gmail.com", "123");
    UserReportDTO dto = new UserReportDTO();
    dto.setReporterId(user.getId());
    dto.setReportType(ReportType.CPF);
    dto.setReportValue("12312312312");

    UserReport report = new UserReport(1L, user, ReportType.CPF, "12312312312", LocalDateTime.now());

    when(userService.findById(1L)).thenReturn(user);
    when(reportService.saveNewReport(eq(1L), any(UserReportDTO.class))).thenReturn(report);

    mockMvc.perform(post(BASE_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isCreated());
}

}
