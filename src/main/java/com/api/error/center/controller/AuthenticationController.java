package com.api.error.center.controller;

import com.api.error.center.dto.TokenDto;
import com.api.error.center.form.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<TokenDto> authenticate(@RequestBody LoginForm loginForm) {
        UsernamePasswordAuthenticationToken login = loginForm.convertToUsernamePasswordAuthenticationToken();

        Authentication authentication = authenticationManager.authenticate(login);

        return null;
    }

}
