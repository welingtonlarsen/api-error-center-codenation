package com.api.error.center.controller;

import com.api.error.center.entity.Source;
import com.api.error.center.entity.SourceProfile;
import com.api.error.center.form.NewSourceForm;
import com.api.error.center.service.SourceProfileService;
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
public class SourceControllerTest {

    @MockBean
    UserService userService;

    @MockBean
    SourceProfileService sourceProfileService;

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    public void testCreateValidUser() throws Exception {
        BDDMockito.given(sourceProfileService.findByProfileName(Mockito.anyString())).willReturn(Optional.of(getMockedUserProfile()));
        BDDMockito.given(userService.save(Mockito.any(Source.class))).willReturn(getMockedUser());

        mockMvc.perform(MockMvcRequestBuilders.post("/user").content(getValidUserFormPayLoad())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
    }

    private String getValidUserFormPayLoad() throws JsonProcessingException {
        NewSourceForm userForm = new NewSourceForm();
        userForm.setProfileName("SYSTEM");
        userForm.setUsername("mocked user");
        userForm.setPassword("mocked password");
        return new ObjectMapper().writeValueAsString(userForm);
    }

    private Source getMockedUser() {
        Source source = new Source("mocked user", "mockeduser", getMockedUserProfile());
        source.setId(1L);
        return source;
    }

    private SourceProfile getMockedUserProfile() {
        SourceProfile sourceProfile = new SourceProfile("Profile Test");
        sourceProfile.setId(1L);
        return sourceProfile;
    }
}
