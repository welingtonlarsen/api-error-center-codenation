package com.api.error.center.service;

import com.api.error.center.entity.LogEvent;
import com.api.error.center.entity.User;
import com.api.error.center.enums.Level;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LogEventService {
    LogEvent save(LogEvent logEvent);

    Optional<LogEvent> findById(Long id);

    List<LogEvent> findAllByFilters(Level level, String description, String log, User source, LocalDateTime startDate, LocalDateTime endDate, Integer quantity);
}
