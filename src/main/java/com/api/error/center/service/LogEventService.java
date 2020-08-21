package com.api.error.center.service;

import com.api.error.center.entity.LogEvent;
import com.api.error.center.entity.Source;
import com.api.error.center.enums.Level;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

public interface LogEventService {
    LogEvent save(LogEvent logEvent);

    Optional<LogEvent> findById(Long id);

    Page<LogEvent> findAllByFilters(Level level, String description, String log, Source source, LocalDateTime startDate, LocalDateTime endDate, Integer quantity, Pageable pageable);
}
