package com.api.error.center.form;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class LoginForm extends SourceForm {

    public UsernamePasswordAuthenticationToken convertToUsernamePasswordAuthenticationToken() {
        return new UsernamePasswordAuthenticationToken(username, password);
    }
}
