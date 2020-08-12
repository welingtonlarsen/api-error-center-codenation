package com.api.error.center.dto;

public class NewUserDto {

    private Long id;
    private String username;
    private String userProfile;

    public NewUserDto(Long id, String username, String userProfile) {
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
