package com.api.error.center.dto;

import com.api.error.center.entity.LogEvent;

import java.time.LocalDateTime;

public class LogEventDto {

    private Long id;
    private String level;
    private String description;
    private String log;
    private Long sourceId;
    private LocalDateTime date;
    private int quantity;

    public static LogEventDto convertEntityToDto(LogEvent logEvent) {
        return new LogEventDto(logEvent.getId(), logEvent.getLevel().toString(),
                logEvent.getDescription(), logEvent.getLog(), logEvent.getSource().getId(), logEvent.getDate(), logEvent.getQuantity());
    }

    private LogEventDto(Long id, String level, String description, String log, Long sourceId, LocalDateTime date, int quantity) {
        this.id = id;
        this.level = level;
        this.description = description;
        this.log = log;
        this.sourceId = sourceId;
        this.date = date;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public String getLevel() {
        return level;
    }

    public String getDescription() {
        return description;
    }

    public String getLog() {
        return log;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public int getQuantity() {
        return quantity;
    }

}
