package com.api.error.center.service.impl;

import com.api.error.center.entity.LogEvent;
import com.api.error.center.entity.User;
import com.api.error.center.enums.Level;
import com.api.error.center.repository.LogEventRepository;
import com.api.error.center.service.LogEventService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<LogEvent> findAllByFilters(Level level, String description, String log, User source, LocalDateTime startDate, LocalDateTime endDate, Integer quantity) {
        return logEventRepository.findAllByFilters(level, description, log, source, startDate, endDate, quantity);
    }
}
