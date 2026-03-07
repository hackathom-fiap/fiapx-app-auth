package com.fiapx.auth.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRegistrationRequest {
    @NotBlank String username;
    @NotBlank @Email String email;
    @NotBlank String password;
}
