package com.api.error.center.service.impl;

import com.api.error.center.entity.SourceProfile;
import com.api.error.center.repository.SourceProfileRepository;
import com.api.error.center.service.SourceProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SourceProfileServiceImpl implements SourceProfileService {

    @Autowired
    private SourceProfileRepository sourceProfileRepository;

    @Override
    public SourceProfile save(SourceProfile sourceProfile) {
        return sourceProfileRepository.save(sourceProfile);
    }

    @Override
    public Optional<SourceProfile> findById(Long id) {
        return sourceProfileRepository.findById(id);
    }

    @Override
    public Optional<SourceProfile> findByProfileName(String profileName) {
        return sourceProfileRepository.findByProfileName(profileName);
    }
}
