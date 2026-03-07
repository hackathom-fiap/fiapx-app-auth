package com.fiapx.auth.application.usecase;

import com.fiapx.auth.application.dto.LoginRequest;
import com.fiapx.auth.application.dto.LoginResponse;
import com.fiapx.auth.domain.entity.User;
import com.fiapx.auth.domain.repository.UserRepository;
import com.fiapx.auth.domain.service.PasswordEncoderPort;
import com.fiapx.auth.domain.service.TokenServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoderPort passwordEncoder;
    private final TokenServicePort tokenService;

    public LoginResponse execute(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuário ou senha inválidos"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Usuário ou senha inválidos");
        }

        String token = tokenService.generateToken(user);

        return LoginResponse.builder()
                .token(token)
                .username(user.getUsername())
                .build();
    }
}
