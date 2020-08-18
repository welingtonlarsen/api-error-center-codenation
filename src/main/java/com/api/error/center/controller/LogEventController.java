package com.api.error.center.controller;

import com.api.error.center.dto.LogEventDto;
import com.api.error.center.dto.LogEventToListDto;
import com.api.error.center.entity.LogEvent;
import com.api.error.center.entity.User;
import com.api.error.center.enums.Level;
import com.api.error.center.form.LogEventForm;
import com.api.error.center.response.Response;
import com.api.error.center.service.LogEventService;
import com.api.error.center.service.UserService;
import com.api.error.center.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/log")
public class LogEventController {

    @Autowired
    private LogEventService logEventService;

    @Autowired
    private UserService userService;

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

    @GetMapping(value = "/{logEventId}")
    public ResponseEntity<Response<LogEventDto>> findById(@PathVariable("logEventId") Long logEventId) {
        Response<LogEventDto> response = new Response<>();

        Optional<LogEvent> logEvent = logEventService.findById(logEventId);
        if (logEvent.isPresent()) {
            LogEventDto logEventDto = convertEntityToDto(logEvent.get());
            response.setData(logEventDto);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Response<Page<LogEventToListDto>>> findAllByFilters(@RequestParam(required = false) Level level,
                                                                              @RequestParam(required = false) String description,
                                                                              @RequestParam(required = false) String log,
                                                                              @RequestParam(required = false) Long sourceId,
                                                                              @RequestParam(required = false) String startDateIn,
                                                                              @RequestParam(required = false) String endDateIn,
                                                                              @RequestParam(required = false) Integer quantity,
                                                                              @PageableDefault(sort = "id", direction = Sort.Direction.ASC, page = 0, size = 10) Pageable pageable) {
        Response<Page<LogEventToListDto>> response = new Response<>();

        User user = null;
        if (sourceId != null) {
            Optional<User> source = userService.findById(sourceId);
            if (!source.isPresent()) {
                response.addError("source with id " + sourceId + " dont't exist");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            user = source.get();
        }

       if ((startDateIn != null && !DateUtil.validateDateWithRegex(startDateIn)) || (endDateIn != null && !DateUtil.validateDateWithRegex(endDateIn))) {
           response.addError("date must be in the format: dd-MM-yyyy HH:mm:ss");
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
       }

        Page<LogEvent> logEvents = logEventService.findAllByFilters(level, description, log, user, DateUtil.convertStringToLocalDateTime(startDateIn), DateUtil.convertStringToLocalDateTime(endDateIn), quantity, pageable);
        Page<LogEventToListDto> logEventDtos = convertListEntitiesToListDtos(logEvents);
        response.setData(logEventDtos);

        return ResponseEntity.ok(response);
    }

    private LogEvent convertFormToEntity(LogEventForm logEventForm, User user) {
        return new LogEvent(Level.valueOf(logEventForm.getLevel()), logEventForm.getDescription(), logEventForm.getLog(),
                user, DateUtil.convertStringToLocalDateTime(logEventForm.getDate()), logEventForm.getQuantity());
    }

    private LogEventDto convertEntityToDto(LogEvent logEvent) {
        return new LogEventDto(logEvent.getId(), logEvent.getLevel().toString(),
                logEvent.getDescription(), logEvent.getLog(), logEvent.getSource().getId(), logEvent.getDate(), logEvent.getQuantity());
    }

    private Page<LogEventToListDto> convertListEntitiesToListDtos(Page<LogEvent> logEvents) {
        Page<LogEventToListDto> dto = logEvents.map(new Function<LogEvent, LogEventToListDto>() {
            @Override
            public LogEventToListDto apply(LogEvent logEvent) {
                LogEventToListDto logEventToPageDto = new LogEventToListDto(logEvent.getId(), logEvent.getLevel().toString(), logEvent.getDescription(),
                        logEvent.getSource().getId(),logEvent.getDate(), logEvent.getQuantity());
                return logEventToPageDto;
            }
        });
        return dto;
    };

}
