package com.api.error.center.controller;

import com.api.error.center.dto.LogEventDto;
import com.api.error.center.entity.LogEvent;
import com.api.error.center.entity.User;
import com.api.error.center.enums.Level;
import com.api.error.center.form.LogEventForm;
import com.api.error.center.response.Response;
import com.api.error.center.service.LogEventService;
import com.api.error.center.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/log")
public class LogEventController {

    @Autowired
    private LogEventService logEventService;

    @PostMapping
    public ResponseEntity<Response<LogEventDto>> createLogEvent(@RequestBody @Valid LogEventForm logEventForm, BindingResult bindingResult, @AuthenticationPrincipal User user) {

        Response<LogEventDto> response = new Response<>();

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        LogEvent logEvent = logEventService.save(convertFormToEntity(logEventForm, user));
        LogEventDto logEventDto = convertEntityToDto(logEvent);

        response.setData(logEventDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    private LogEvent convertFormToEntity(LogEventForm logEventForm, User user) {
        return new LogEvent(Level.valueOf(logEventForm.getLevel()), logEventForm.getDescription(), logEventForm.getLog(),
                user, DateUtil.convertStringToLocalDateTime(logEventForm.getDate()), logEventForm.getQuantity());
    }

    private LogEventDto convertEntityToDto(LogEvent logEvent) {
        return new LogEventDto(logEvent.getId(), logEvent.getLevel().toString(),
                logEvent.getDescription(), logEvent.getLog(), logEvent.getSource().getId(), logEvent.getDate(), logEvent.getQuantity());
    }
}
