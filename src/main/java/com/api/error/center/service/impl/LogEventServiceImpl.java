package com.api.error.center.service.impl;

import com.api.error.center.entity.LogEvent;
import com.api.error.center.entity.User;
import com.api.error.center.enums.Level;
import com.api.error.center.repository.LogEventRepository;
import com.api.error.center.service.LogEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LogEventServiceImpl implements LogEventService {

    @Autowired
    LogEventRepository logEventRepository;

    @Override
    public LogEvent save(LogEvent logEvent) {
        return logEventRepository.save(logEvent);
    }

    @Override
    public Optional<LogEvent> findById(Long id) {
        return logEventRepository.findById(id);
    }

    @Override
    public Page<LogEvent> findAllByFilters(Level level, String description, String log, User source, LocalDateTime startDate, LocalDateTime endDate, Integer quantity, Pageable pageable) {
        if (startDate == null) {
            startDate = LocalDateTime.of(1900, 1, 1, 0, 0, 0);
        }
        if (endDate == null) {
            endDate = LocalDateTime.now();
        }

        return logEventRepository.findAllByFilters(level, description, log, source, startDate, endDate, quantity, pageable);
    }
}
