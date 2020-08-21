package com.api.error.center.service.impl;

import com.api.error.center.entity.SourceProfile;
import com.api.error.center.repository.UserProfileRepository;
import com.api.error.center.service.SourceProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SourceProfileServiceImpl implements SourceProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Override
    public SourceProfile save(SourceProfile sourceProfile) {
        return userProfileRepository.save(sourceProfile);
    }

    @Override
    public Optional<SourceProfile> findById(Long id) {
        return userProfileRepository.findById(id);
    }

    @Override
    public Optional<SourceProfile> findByProfileName(String profileName) {
        return userProfileRepository.findByProfileName(profileName);
    }
}
