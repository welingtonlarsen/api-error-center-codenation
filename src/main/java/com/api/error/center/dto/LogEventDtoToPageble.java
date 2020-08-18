package com.api.error.center.dto;

import java.time.LocalDateTime;

public class LogEventDtoToPageble {

    private Long id;
    private String level;
    private String description;
    private Long userId;
    private LocalDateTime date;
    private int quantity;

    public LogEventDtoToPageble(Long id, String level, String description, Long userId, LocalDateTime date, int quantity) {
        this.id = id;
        this.level = level;
        this.description = description;
        this.userId = userId;
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

    public Long getUserId() {
        return userId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public int getQuantity() {
        return quantity;
    }

}
