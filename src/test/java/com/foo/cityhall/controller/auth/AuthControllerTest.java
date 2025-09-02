package com.foo.cityhall.controller.auth;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foo.cityhall.model.dto.auth.AuthDTO;
import com.foo.cityhall.model.dto.auth.AuthResponse;
import com.foo.cityhall.model.dto.auth.TokenValidationResponse;
import com.foo.cityhall.service.auth.AuthService;

import jakarta.servlet.http.HttpServletRequest;

@WebMvcTest(controllers = AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void login_ReturnsAuthResponse() throws Exception {
        AuthResponse resp = new AuthResponse();
        when(authService.login(any(AuthDTO.class))).thenReturn(resp);

        mockMvc.perform(post("/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(resp)));

        verify(authService).login(any(AuthDTO.class));
    }

    @Test
    void validate_ReturnsTokenValidationResponse() throws Exception {
        TokenValidationResponse tv = new TokenValidationResponse();
        when(authService.validateToken(any(HttpServletRequest.class))).thenReturn(tv);

        mockMvc.perform(post("/auth/validate")
                        .header("Authorization", "Bearer fake-token"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(tv)));

        verify(authService).validateToken(any(HttpServletRequest.class));
    }
}
