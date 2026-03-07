package com.fiapx.auth.application.usecase;

import com.fiapx.auth.application.dto.UserRegistrationRequest;
import com.fiapx.auth.domain.entity.User;
import com.fiapx.auth.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterUserUseCase {

    private final UserRepository userRepository;
    private final com.fiapx.auth.domain.service.PasswordEncoderPort passwordEncoder;

    public User execute(UserRegistrationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Usuário já existe");
        }
        
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("E-mail já cadastrado");
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        return userRepository.save(user);
    }
}
