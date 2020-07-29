package com.api.error.center.repository;

import com.api.error.center.entity.LogEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogEventRepository extends JpaRepository<LogEvent, Long> {
}
