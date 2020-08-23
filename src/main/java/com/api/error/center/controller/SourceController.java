package com.api.error.center.controller;

import com.api.error.center.dto.NewUserDto;
import com.api.error.center.entity.Source;
import com.api.error.center.entity.SourceProfile;
import com.api.error.center.form.NewSourceForm;
import com.api.error.center.response.Response;
import com.api.error.center.service.SourceProfileService;
import com.api.error.center.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class SourceController {

    @Autowired
    UserService sourceService;

    @Autowired
    SourceProfileService sourceProfileService;

    @PostMapping
    public ResponseEntity<Response<NewUserDto>> createSource(@RequestBody @Valid NewSourceForm newSourceForm, BindingResult bindingResult) {
        Response<NewUserDto> response = new Response<>();

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> response.addError(error.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Optional<SourceProfile> sourceProfile = sourceProfileService.findByProfileName(newSourceForm.getProfileName());

        if (sourceProfile.isPresent()) {
            Source newSource = newSourceForm.convertNewUserFormToEntity(newSourceForm, sourceProfile.get());
            newSource = sourceService.save(newSource);
            response.setData(NewUserDto.converSourceToNewUserDto(newSource));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            response.addError("There isn't this User Profile. Please, contact with administration.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

}
