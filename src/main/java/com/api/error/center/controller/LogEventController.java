package com.api.error.center.controller;

import com.api.error.center.controller.validation.LogEventControllerValidations;
import com.api.error.center.dto.LogEventDto;
import com.api.error.center.dto.LogEventDtoToPageble;
import com.api.error.center.entity.LogEvent;
import com.api.error.center.entity.Source;
import com.api.error.center.enums.Level;
import com.api.error.center.form.LogEventForm;
import com.api.error.center.response.Response;
import com.api.error.center.service.LogEventService;
import com.api.error.center.service.UserService;
import com.api.error.center.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.function.Function;

@RestController
@RequestMapping("/log")
public class LogEventController extends LogEventControllerValidations {

    @Autowired
    private LogEventService logEventService;

    @Autowired
    private UserService sourceService;

    @PostMapping
    public ResponseEntity<Response<LogEventDto>> createLogEvent(@RequestBody @Valid LogEventForm logEventForm, BindingResult bindingResult, @AuthenticationPrincipal Source source) {
        Response<LogEventDto> response = new Response<>();

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> response.addError(error.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        LogEvent logEvent = logEventService.save(logEventForm.convertFormToEntity(logEventForm, source));
        LogEventDto logEventDto = LogEventDto.convertEntityToDto(logEvent);

        response.setData(logEventDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(value = "/{logEventId}")
    public ResponseEntity<Response<LogEventDto>> findById(@PathVariable("logEventId") Long logEventId) {
        Response<LogEventDto> response = new Response<>();

        Optional<LogEvent> logEvent = logEventService.findById(logEventId);
        if (logEvent.isPresent()) {
            LogEventDto logEventDto = LogEventDto.convertEntityToDto(logEvent.get());
            response.setData(logEventDto);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Response<Page<LogEventDtoToPageble>>> findAllByFilters(@RequestParam(required = false) Level level,
                                                                                 @RequestParam(required = false) String description,
                                                                                 @RequestParam(required = false) String log,
                                                                                 @RequestParam(required = false) Long sourceId,
                                                                                 @RequestParam(required = false) String startDateIn,
                                                                                 @RequestParam(required = false) String endDateIn,
                                                                                 @RequestParam(required = false) Integer quantity,
                                                                                 @PageableDefault/*(sort = "id", direction = Sort.Direction.ASC, page = 0, size = 10)*/ Pageable pageable) {
        Response<Page<LogEventDtoToPageble>> response = new Response<>();

        Source source = getSourceFromRequestParam(sourceId);
        if (!validateFindAllByFiltersRequestParams(sourceId, source, startDateIn, endDateIn, response))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

        Page<LogEvent> logEvents = logEventService.findAllByFilters(level, description, log, source, DateUtil.convertStringToLocalDateTime(startDateIn), DateUtil.convertStringToLocalDateTime(endDateIn), quantity, pageable);
        Page<LogEventDtoToPageble> logEventDtos = convertPageEntityToPageDto(logEvents);

        response.setData(logEventDtos);
        return ResponseEntity.ok(response);
    }

    private Page<LogEventDtoToPageble> convertPageEntityToPageDto(Page<LogEvent> logEvents) {
        Page<LogEventDtoToPageble> pageLogEventDto = logEvents.map(new Function<LogEvent, LogEventDtoToPageble>() {
            @Override
            public LogEventDtoToPageble apply(LogEvent logEvent) {
                LogEventDtoToPageble logEventDtoToPageble = new LogEventDtoToPageble(logEvent.getId(), logEvent.getLevel().toString(), logEvent.getDescription(),
                        logEvent.getSource().getId(), logEvent.getDate(), logEvent.getQuantity());
                return logEventDtoToPageble;
            }
        });
        return pageLogEventDto;
    }

    private Source getSourceFromRequestParam(Long sourceId) {
        Source user = null;
        if (sourceId != null) {
            Optional<Source> source = sourceService.findById(sourceId);
            user = source.isPresent() ? source.get() : null;
        }
        return user;
    }

}
