package com.fiapx.auth.domain.service;

import com.fiapx.auth.domain.entity.User;

public interface TokenServicePort {
    String generateToken(User user);
    String validateToken(String token);
}
