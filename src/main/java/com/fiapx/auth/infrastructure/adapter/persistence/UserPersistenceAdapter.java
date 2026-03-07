package com.fiapx.auth.infrastructure.adapter.persistence;

import com.fiapx.auth.domain.entity.User;
import com.fiapx.auth.domain.repository.UserRepository;
import com.fiapx.auth.infrastructure.adapter.persistence.entity.UserEntity;
import com.fiapx.auth.infrastructure.adapter.persistence.repository.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserRepository {

    private final JpaUserRepository jpaRepository;

    @Override
    public User save(User user) {
        UserEntity entity = UserEntity.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
        UserEntity saved = jpaRepository.save(entity);
        return mapToDomain(saved);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return jpaRepository.findByUsername(username).map(this::mapToDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(this::mapToDomain);
    }

    @Override
    public boolean existsByUsername(String username) {
        return jpaRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    private User mapToDomain(UserEntity entity) {
        return User.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .build();
    }
}
