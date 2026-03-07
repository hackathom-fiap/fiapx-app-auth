package com.fiapx.auth.infrastructure.adapter.web;

import com.fiapx.auth.application.dto.UserRegistrationRequest;
import com.fiapx.auth.application.usecase.RegisterUserUseCase;
import com.fiapx.auth.domain.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;
    private final com.fiapx.auth.application.usecase.LoginUserUseCase loginUserUseCase;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User register(@RequestBody @Valid com.fiapx.auth.application.dto.UserRegistrationRequest request) {
        return registerUserUseCase.execute(request);
    }

    @PostMapping("/login")
    public com.fiapx.auth.application.dto.LoginResponse login(@RequestBody @Valid com.fiapx.auth.application.dto.LoginRequest request) {
        return loginUserUseCase.execute(request);
    }
}
