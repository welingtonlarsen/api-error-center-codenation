package com.api.error.center.controller;

import com.api.error.center.dto.LogEventDto;
import com.api.error.center.entity.LogEvent;
import com.api.error.center.enums.Level;
import com.api.error.center.form.LogEventForm;
import com.api.error.center.response.Response;
import com.api.error.center.service.LogEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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
    public ResponseEntity<Response<LogEventDto>> createLogEvent(@RequestBody @Valid LogEventForm logEventForm, BindingResult bindingResult) {

        Response<LogEventDto> response = new Response<>();

        if(bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        convertFormToEntity(logEventForm);


        return null;
    }

    private LogEvent convertFormToEntity(LogEventForm logEventForm) {
        Level level = Level.valueOf(logEventForm.getLevel());
        //Usuario do token
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime date = LocalDateTime.parse(logEventForm.getDate(), dateTimeFormatter);
        /*
            Criar utilitario para datas
            Encontrar token do usuario e converter para Usuario
         */

        return null;
    }

}
