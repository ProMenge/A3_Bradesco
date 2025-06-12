package com.a3bradesco.api.auth;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.a3bradesco.api.config.security.CustomUserDetailsService;
import com.a3bradesco.api.config.security.SecurityConfig;
import com.a3bradesco.api.config.security.TestSecurityConfig;
import com.a3bradesco.api.config.security.jwt.JwtService;
import com.a3bradesco.api.controllers.TestController;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

@WebMvcTest(controllers = TestController.class)
@Import(TestSecurityConfig.class) // <--- Importa a config personalizada!
@AutoConfigureMockMvc(addFilters = true)
// Mantém os filtros (o JwtAuthFilter será testado!)
class JwtAuthFilterTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void shouldAllowPublicUserCreationWithoutToken() throws Exception {
        mockMvc.perform(post("/test/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"john\", \"password\": \"123\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldBlockProtectedEndpointWithoutToken() throws Exception {
        mockMvc.perform(get("/test/private"))
                .andExpect(status().isForbidden()); // porque não há token
    }

    @Test
    void shouldAllowAccessWithValidToken() throws Exception {
        String token = "valid.token.here";
        String username = "user";

        UserDetails userDetails = new User(username, "password", Collections.emptyList());

        when(jwtService.extractUsername(token)).thenReturn(username);
        when(customUserDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(jwtService.isTokenValid(token, userDetails)).thenReturn(true);

        mockMvc.perform(get("/test/private")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }
}
