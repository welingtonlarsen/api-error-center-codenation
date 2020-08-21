package com.api.error.center.repository;

import com.api.error.center.entity.Source;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Source, Long> {

    Optional<Source> findByUsername(String username);
}
