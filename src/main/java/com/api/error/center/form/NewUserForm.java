package com.api.error.center.form;

import com.api.error.center.entity.Source;
import com.api.error.center.entity.SourceProfile;
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

    public Source convertNewUserFormToEntity(NewUserForm newUserForm, SourceProfile sourceProfile) {
        return new Source(username, BCryptUtil.enconde(password), sourceProfile);
    }
}
