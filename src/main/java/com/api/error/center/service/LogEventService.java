package com.api.error.center.service;

import com.api.error.center.entity.LogEvent;

import java.util.Optional;

public interface LogEventService {
    LogEvent save(LogEvent logEvent);

    Optional<LogEvent> findById(Long id);
}
