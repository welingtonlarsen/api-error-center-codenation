package com.api.error.center.service;

import org.springframework.security.core.Authentication;

public interface TokenService {

    String generateToken(Authentication authentication);

    boolean validToken(String token);

    Long getUserIdFromToken(String token);
}
