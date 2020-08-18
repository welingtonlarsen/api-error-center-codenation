package com.api.error.center.form;

import com.api.error.center.entity.User;
import com.api.error.center.entity.UserProfile;
import com.api.error.center.util.BCryptUtil;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class NewUserForm extends UserForm {

    @Pattern(regexp="^(SYSTEM)$", message = "Are accepted for profileName only SYSTEM")
    @NotNull(message = "profileName must not be null")
    private String profileName;

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public User convertNewUserFormToEntity(NewUserForm newUserForm, UserProfile userProfile) {
        return new User(username, BCryptUtil.enconde(password), userProfile);
    }
}
