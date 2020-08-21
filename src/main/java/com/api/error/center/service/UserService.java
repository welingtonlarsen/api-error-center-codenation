package com.api.error.center.service;

import com.api.error.center.entity.Source;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {

    Optional<Source> findByUsername(String username);

    Optional<Source> findById(Long userId);

    Source save(Source source);

}
