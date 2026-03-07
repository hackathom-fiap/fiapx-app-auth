package com.fiapx.auth.infrastructure.adapter.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiapx.auth.application.dto.LoginRequest;
import com.fiapx.auth.application.dto.LoginResponse;
import com.fiapx.auth.application.dto.UserRegistrationRequest;
import com.fiapx.auth.application.usecase.LoginUserUseCase;
import com.fiapx.auth.application.usecase.RegisterUserUseCase;
import com.fiapx.auth.domain.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AuthController.class)
@Import(TestSecurityConfig.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RegisterUserUseCase registerUserUseCase;

    @MockBean
    private LoginUserUseCase loginUserUseCase;

    private UserRegistrationRequest validRegistrationRequest;
    private LoginRequest validLoginRequest;
    private User mockUser;
    private LoginResponse mockLoginResponse;

    @BeforeEach
    void setUp() {
        validRegistrationRequest = new UserRegistrationRequest();
        validRegistrationRequest.setUsername("testuser");
        validRegistrationRequest.setEmail("test@example.com");
        validRegistrationRequest.setPassword("password123");

        validLoginRequest = LoginRequest.builder()
                .username("testuser")
                .password("password123")
                .build();

        mockUser = User.builder()
                .id(UUID.randomUUID())
                .username("testuser")
                .email("test@example.com")
                .password("encodedPassword")
                .build();

        mockLoginResponse = LoginResponse.builder()
                .token("jwt-token")
                .username("testuser")
                .build();
    }

    @Test
    @DisplayName("Should register user successfully")
    void shouldRegisterUserSuccessfully() throws Exception {
        when(registerUserUseCase.execute(any(UserRegistrationRequest.class))).thenReturn(mockUser);

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRegistrationRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(mockUser.getId().toString()))
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.password").value("encodedPassword"));
    }

    @Test
    @DisplayName("Should return error when registering with empty username")
    void shouldReturnErrorWhenRegisterWithEmptyUsername() throws Exception {
        validRegistrationRequest.setUsername("");

        when(registerUserUseCase.execute(any(UserRegistrationRequest.class)))
                .thenThrow(new RuntimeException("Username não pode ser vazio"));

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRegistrationRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return error when registering with invalid email")
    void shouldReturnErrorWhenRegisterWithInvalidEmail() throws Exception {
        validRegistrationRequest.setEmail("invalid-email");

        when(registerUserUseCase.execute(any(UserRegistrationRequest.class)))
                .thenThrow(new RuntimeException("Email inválido"));

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRegistrationRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return error when registering with empty password")
    void shouldReturnErrorWhenRegisterWithEmptyPassword() throws Exception {
        validRegistrationRequest.setPassword("");

        when(registerUserUseCase.execute(any(UserRegistrationRequest.class)))
                .thenThrow(new RuntimeException("Senha inválida"));

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRegistrationRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return error when registering with empty body")
    void shouldReturnErrorWhenRegisterWithEmptyBody() throws Exception {
        when(registerUserUseCase.execute(any(UserRegistrationRequest.class)))
                .thenThrow(new RuntimeException("Dados inválidos"));

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return error when registering without content-type")
    void shouldReturnErrorWhenRegisterWithoutContentType() throws Exception {
        mockMvc.perform(post("/api/auth/register")
                .content(objectMapper.writeValueAsString(validRegistrationRequest)))
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    @DisplayName("Should login successfully")
    void shouldLoginSuccessfully() throws Exception {
        when(loginUserUseCase.execute(any(LoginRequest.class))).thenReturn(mockLoginResponse);

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validLoginRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.token").value("jwt-token"))
                .andExpect(jsonPath("$.username").value("testuser"));
    }

    @Test
    @DisplayName("Should return error when logging in with empty username")
    void shouldReturnErrorWhenLoginWithEmptyUsername() throws Exception {
        LoginRequest invalidRequest = LoginRequest.builder()
                .username("")
                .password("password123")
                .build();

        when(loginUserUseCase.execute(any(LoginRequest.class)))
                .thenThrow(new RuntimeException("Usuário ou senha inválidos"));

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return error when logging in with empty password")
    void shouldReturnErrorWhenLoginWithEmptyPassword() throws Exception {
        LoginRequest invalidRequest = LoginRequest.builder()
                .username("testuser")
                .password("")
                .build();

        when(loginUserUseCase.execute(any(LoginRequest.class)))
                .thenThrow(new RuntimeException("Usuário ou senha inválidos"));

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return error when logging in with empty body")
    void shouldReturnErrorWhenLoginWithEmptyBody() throws Exception {
        when(loginUserUseCase.execute(any(LoginRequest.class)))
                .thenThrow(new RuntimeException("Credenciais inválidas"));

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return error when logging in without content-type")
    void shouldReturnErrorWhenLoginWithoutContentType() throws Exception {
        mockMvc.perform(post("/api/auth/login")
                .content(objectMapper.writeValueAsString(validLoginRequest)))
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    @DisplayName("Should propagate exception from RegisterUserUseCase")
    void shouldPropagateExceptionFromRegisterUserUseCase() throws Exception {
        when(registerUserUseCase.execute(any(UserRegistrationRequest.class)))
                .thenThrow(new RuntimeException("Usuário já existe"));

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRegistrationRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should propagate exception from LoginUserUseCase")
    void shouldPropagateExceptionFromLoginUserUseCase() throws Exception {
        when(loginUserUseCase.execute(any(LoginRequest.class)))
                .thenThrow(new RuntimeException("Usuário ou senha inválidos"));

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validLoginRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should register user with valid email")
    void shouldRegisterUserWithValidEmail() throws Exception {
        validRegistrationRequest.setEmail("user.name+tag@domain.co.uk");

        when(registerUserUseCase.execute(any(UserRegistrationRequest.class))).thenReturn(mockUser);

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRegistrationRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Should login with valid username")
    void shouldLoginWithValidUsername() throws Exception {
        LoginRequest requestWithNumbers = LoginRequest.builder()
                .username("user123")
                .password("password123")
                .build();

        when(loginUserUseCase.execute(any(LoginRequest.class))).thenReturn(mockLoginResponse);

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestWithNumbers)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("jwt-token"));
    }

    @Test
    @DisplayName("Should return error for unsupported HTTP method")
    void shouldReturnErrorForUnsupportedHttpMethod() throws Exception {
        mockMvc.perform(get("/api/auth/register"))
                .andExpect(status().isMethodNotAllowed());

        mockMvc.perform(get("/api/auth/login"))
                .andExpect(status().isMethodNotAllowed());
    }
}
