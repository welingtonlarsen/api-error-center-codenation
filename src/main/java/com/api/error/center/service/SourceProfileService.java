package com.api.error.center.service;

import com.api.error.center.entity.SourceProfile;

import java.util.Optional;

public interface SourceProfileService {

    SourceProfile save(SourceProfile sourceProfile);
    Optional<SourceProfile> findById(Long id);
    Optional<SourceProfile> findByProfileName(String profileName);

}
