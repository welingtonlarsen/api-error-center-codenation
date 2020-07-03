package com.api.error.center.controller;

import com.api.error.center.dto.TokenDto;
import com.api.error.center.form.LoginForm;
import com.api.error.center.response.Response;
import com.api.error.center.service.impl.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<Response<TokenDto>> authenticate(@RequestBody LoginForm loginForm) {
        Response<TokenDto> response = new Response<>();
        UsernamePasswordAuthenticationToken login = loginForm.convertToUsernamePasswordAuthenticationToken();
        try {
            Authentication authentication = authenticationManager.authenticate(login);
            String token = tokenService.generateToken(authentication);

            TokenDto tokenDto = new TokenDto(token, "Bearer");
            response.setData(tokenDto);

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        } catch (AuthenticationException e) {
            response.getErrors().add("Incorrect login and/or password");

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

    }

}
