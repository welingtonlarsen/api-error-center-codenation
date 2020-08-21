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

    public void setLevel(Level level) {
        this.level = level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "LogEvent{" +
                ", level=" + level +
                ", description='" + description + '\'' +
                ", log='" + log + '\'' +
                ", source=" + source +
                ", date=" + date +
                ", quantity=" + quantity +
                '}';
    }
}
