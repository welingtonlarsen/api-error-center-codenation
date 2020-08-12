package com.api.error.center.controller;

import com.api.error.center.entity.User;
import com.api.error.center.entity.UserProfile;
import com.api.error.center.form.NewUserForm;
import com.api.error.center.service.UserProfileService;
import com.api.error.center.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {

    @MockBean
    UserService userService;

    @MockBean
    UserProfileService userProfileService;

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    public void testCreateValidUser() throws Exception {
        BDDMockito.given(userProfileService.findByProfileName(Mockito.anyString())).willReturn(Optional.of(getMockedUserProfile()));
        BDDMockito.given(userService.save(Mockito.any(User.class))).willReturn(getMockedUser());

        mockMvc.perform(MockMvcRequestBuilders.post("/user").content(getValidUserFormPayLoad())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
    }

    private String getValidUserFormPayLoad() throws JsonProcessingException {
        NewUserForm userForm = new NewUserForm();
        userForm.setProfileName("SYSTEM");
        userForm.setUsername("mocked user");
        userForm.setPassword("mocked password");
        return new ObjectMapper().writeValueAsString(userForm);
    }

    private User getMockedUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("mocked user");
        user.setPassword("mockeduser");
        user.setUserProfile(getMockedUserProfile());
        return user;
    }

    private UserProfile getMockedUserProfile() {
        UserProfile userProfile = new UserProfile();
        userProfile.setId(1L);
        userProfile.setProfileName("Profile Test");
        return userProfile;
    }
}
