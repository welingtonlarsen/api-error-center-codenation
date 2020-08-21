package com.api.error.center.service.impl;

import com.api.error.center.entity.Source;
import com.api.error.center.repository.UserRepository;
import com.api.error.center.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Source> user = findByUsername(username);

        if (user.isPresent()) {
            return user.get();
        }

        throw new UsernameNotFoundException("Invalid Username");
    }

    @Override
    public Optional<Source> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<Source> findById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Source save(Source source) {
        return userRepository.save(source);
    }
}
