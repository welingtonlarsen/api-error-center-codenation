package com.api.error.center.entity;

import com.api.error.center.enums.Level;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class LogEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Level level;

    @NotNull
    private String description;

    @NotNull
    private String log;

    @ManyToOne
    @NotNull
    private Source source;

    @NotNull
    private LocalDateTime date;

    @NotNull
    private int quantity;

    public LogEvent() {
    }

    public LogEvent(Level level, String description, String log, Source source, LocalDateTime date, int quantity) {
        this.level = level;
        this.description = description;
        this.log = log;
        this.source = source;
        this.date = date;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Level getLevel() {
        return level;
    }

    public String getDescription() {
        return description;
    }

    public String getLog() {
        return log;
    }

    public Source getSource() {
        return source;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public int getQuantity() {
        return quantity;
    }

}
