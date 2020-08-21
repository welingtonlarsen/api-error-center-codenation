package com.api.error.center.form;

import com.api.error.center.entity.LogEvent;
import com.api.error.center.entity.Source;
import com.api.error.center.enums.Level;
import com.api.error.center.util.DateUtil;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class LogEventForm {

    @Pattern(regexp="^(ERROR|WARNING|INFO)$", message = "Are accepted for level only ERROR, WARNING or INFO")
    @NotNull(message = "level must not be null")
    private String level;

    @NotNull(message = "description must not be null")
    private String description;

    @NotNull(message = "log must not be null")
    private String log;

    @Pattern(regexp="^([1-9]|([012][0-9])|(3[01]))-([0]{0,1}[1-9]|1[012])-\\d\\d\\d\\d (20|21|22|23|[0-1]?\\d):[0-5]?\\d:[0-5]?\\d$",
            message = "date must be in the format: dd-MM-yyyy HH:mm:ss")
    @NotNull(message = "date must not be null")
    private String date;

    @Range(min = 1, message = "minimum quantity must be 1")
    private int quantity;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LogEvent convertFormToEntity(LogEventForm logEventForm, Source source) {
        return new LogEvent(Level.valueOf(level), description, log, source, DateUtil.convertStringToLocalDateTime(date), quantity);
    }
}
