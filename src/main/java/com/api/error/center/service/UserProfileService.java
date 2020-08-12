package com.api.error.center.service;

import com.api.error.center.entity.UserProfile;

import java.util.Optional;

public interface UserProfileService {

    UserProfile save(UserProfile userProfile);
    Optional<UserProfile> findById(Long id);
    Optional<UserProfile> findByProfileName(String profileName);

}
