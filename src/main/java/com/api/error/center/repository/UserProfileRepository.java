package com.api.error.center.repository;

import com.api.error.center.entity.SourceProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<SourceProfile, Long> {

    Optional<SourceProfile> findByProfileName(String profileName);
}
