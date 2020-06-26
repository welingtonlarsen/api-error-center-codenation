package com.api.error.center.service.impl;

import com.api.error.center.entity.User;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

import io.jsonwebtoken.Jwts;

@Service
public class TokenService {

    @Value("${api.jwt.expiration}")
    private String expiration;

    @Value("${api.jwt.secret}")
    private String secret;

    public String generateToken(Authentication authentication) {
        User authenticatedUser = (User) authentication.getPrincipal();
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + Long.parseLong(expiration));

        return Jwts.builder()
                .setIssuer("API Error Center Codenation")
                .setSubject(authenticatedUser.getId().toString())
                .setIssuedAt(currentDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.ES256, secret)
                .compact();
    }
}
