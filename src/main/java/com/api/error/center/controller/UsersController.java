package com.api.error.center.controller;

import com.api.error.center.dto.NewUserDto;
import com.api.error.center.entity.User;
import com.api.error.center.entity.UserProfile;
import com.api.error.center.form.NewUserForm;
import com.api.error.center.response.Response;
import com.api.error.center.service.UserProfileService;
import com.api.error.center.service.UserService;
import com.api.error.center.util.BCryptUtil;
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
public class UsersController {

    @Autowired
    UserService userService;

    @Autowired
    UserProfileService userProfileService;

    @PostMapping
    public ResponseEntity<Response<NewUserDto>> createUser(@RequestBody @Valid NewUserForm newUserForm, BindingResult bindingResult) {
        Response<NewUserDto> response = new Response<>();

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> response.addError(error.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Optional<UserProfile> userProfile = userProfileService.findByProfileName(newUserForm.getProfileName());

        if (userProfile.isPresent()) {
            User newSource = newUserForm.convertNewUserFormToEntity(newUserForm, userProfile.get());
            newSource = userService.save(newSource);
            response.setData(NewUserDto.converSourceToNewUserDto(newSource));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            response.addError("There isn't this User Profile. Please, contact with administration.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

}
