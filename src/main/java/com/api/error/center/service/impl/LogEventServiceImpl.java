package com.api.error.center.service.impl;

import com.api.error.center.entity.LogEvent;
import com.api.error.center.entity.User;
import com.api.error.center.repository.LogEventRepository;
import com.api.error.center.service.LogEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogEventServiceImpl implements LogEventService {

    @Autowired
    LogEventRepository logEventRepository;

    @Override
    public LogEvent save(LogEvent logEvent) {
        return logEventRepository.save(logEvent);
    }
}