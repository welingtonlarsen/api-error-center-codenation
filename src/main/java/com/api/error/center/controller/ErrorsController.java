package com.api.error.center.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/errors")
public class ErrorsController {

    @GetMapping
    public ResponseEntity saveError(){

        return ResponseEntity.ok().build();
    }
}
