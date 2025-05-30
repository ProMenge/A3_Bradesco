package com.a3bradesco.api.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.a3bradesco.api.dto.UserDTO;
import com.a3bradesco.api.entities.User;
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

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)  // ✅ Desativa filtros de segurança para o teste
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void ReturnUsersList() throws Exception {
        User user = new User(1L, "Igor", "11111111111", "igor@gmail.com", "123");
        when(userService.findAll()).thenReturn(Arrays.asList(user));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk());
    }

    @Test
    void ReturnUserbyId() throws Exception {
        User user = new User(1L, "Igor", "11111111111", "igor@gmail.com", "123");
        when(userService.findById(1L)).thenReturn(user);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk());
    }

    @Test
void CreateNewUser() throws Exception {
    UserDTO dto = new UserDTO();
    dto.setName("Igor");
    dto.setCpf("11111111111");
    dto.setEmail("igor@gmail.com");
    dto.setPassword("123");

    User user = new User(1L, dto.getName(), dto.getCpf(), dto.getEmail(), dto.getPassword());

    when(userService.saveNewUser(any(UserDTO.class))).thenReturn(user);  // ✅ Ajuste aqui!

    mockMvc.perform(post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isCreated());
}

}
