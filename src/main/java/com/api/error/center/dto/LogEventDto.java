package com.api.error.center.dto;

import java.time.LocalDateTime;

public class LogEventDto {

    private Long id;
    private String level;
    private String description;
    private String log;
    private Long userId;
    private LocalDateTime date;
    private int quantity;

    public LogEventDto(Builder builder) {
        this.id = builder.id;
        this.level = builder.level;
        this.description = builder.description;
        this.log = builder.log;
        this.userId = builder.userId;
        this.date = builder.date;
        this.quantity = builder.quantity;
    }

    public static class Builder {
        private Long id;
        private String level;
        private String description;
        private String log;
        private Long userId;
        private LocalDateTime date;
        private int quantity;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder level(String level) {
            this.level = level;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder log(String log) {
            this.log = log;
            return this;
        }

        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder date(LocalDateTime date) {
            this.date = date;
            return this;
        }

        public Builder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public LogEventDto build() {
            return new LogEventDto(this);
        }



    }

}
