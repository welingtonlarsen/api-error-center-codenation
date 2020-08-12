package com.api.error.center.form;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class LoginForm extends UserForm {

    public UsernamePasswordAuthenticationToken convertToUsernamePasswordAuthenticationToken() {
        return new UsernamePasswordAuthenticationToken(username, password);
    }
}
