package com.api.error.center.dto;

import com.api.error.center.entity.Source;

public class NewUserDto {

    private Long id;
    private String username;
    private String userProfile;

    public static NewUserDto converSourceToNewUserDto(Source source) {
        return new NewUserDto(source.getId(), source.getUsername(), source.getSourceProfile().toString());
    }

    private NewUserDto(Long id, String username, String userProfile) {
        this.id = id;
        this.username = username;
        this.userProfile = userProfile;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getUserProfile() {
        return userProfile;
    }
}
