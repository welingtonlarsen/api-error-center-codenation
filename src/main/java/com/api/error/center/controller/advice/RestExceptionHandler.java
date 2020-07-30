package com.api.error.center.controller.advice;

import com.api.error.center.response.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = ex.getCause().getLocalizedMessage();
        error = error.substring(0, error.indexOf("\n"));

        Response<Object> response = new Response<>();
        response.getErrors().add(error);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
