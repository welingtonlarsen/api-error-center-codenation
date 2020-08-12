package com.api.error.center.form;

import javax.validation.constraints.NotNull;

public abstract class UserForm {

    @NotNull(message = "username must not be null")
    protected String username;
    @NotNull(message = "password must not be null")
    protected String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
