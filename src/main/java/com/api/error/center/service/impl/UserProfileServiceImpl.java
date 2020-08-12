package com.api.error.center.service.impl;

import com.api.error.center.entity.UserProfile;
import com.api.error.center.repository.UserProfileRepository;
import com.api.error.center.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Override
    public UserProfile save(UserProfile userProfile) {
        return userProfileRepository.save(userProfile);
    }

    @Override
    public Optional<UserProfile> findById(Long id) {
        return userProfileRepository.findById(id);
    }

    @Override
    public Optional<UserProfile> findByProfileName(String profileName) {
        return userProfileRepository.findByProfileName(profileName);
    }
}
