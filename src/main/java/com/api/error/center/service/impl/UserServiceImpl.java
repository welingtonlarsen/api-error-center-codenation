package com.api.error.center.service.impl;

import com.api.error.center.entity.User;
import com.api.error.center.repository.UserRepository;
import com.api.error.center.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = findByUsername(username);

        if (user.isPresent()) {
            return user.get();
        }

        throw new UsernameNotFoundException("Invalid Username");
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }
}
