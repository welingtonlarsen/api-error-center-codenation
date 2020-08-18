package com.api.error.center.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptUtil {

    public static String enconde(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
}
