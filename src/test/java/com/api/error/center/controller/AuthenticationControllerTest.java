package com.api.error.center.controller;

import com.api.error.center.entity.Source;
import com.api.error.center.entity.SourceProfile;
import com.api.error.center.form.LoginForm;
import com.api.error.center.repository.SourceProfileRepository;
import com.api.error.center.repository.SourceRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthenticationControllerTest {

    private final String username = "userfortest";
    private final String password = "1234";

    @MockBean
    SourceRepository sourceRepository;

    @Autowired
    MockMvc mvc;

    @Autowired
    SourceProfileRepository sourceProfileRepository;

    @Before
    public void setUp() {
        SourceProfile sourceProfile = new SourceProfile("ADMIN");
        sourceProfileRepository.save(sourceProfile);
    }

    @Test
    public void testAuthenticationWithValidUser() throws Exception {
        BDDMockito.given(sourceRepository.findByUsername(Mockito.anyString())).willReturn(Optional.of(getMockedUser(this.username, this.password)));

        mvc.perform(MockMvcRequestBuilders.post("/authentication").content(getJsonPayload(this.username, this.password))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.data.token").isNotEmpty())
                .andExpect(jsonPath("$.data.token").isString())
                .andExpect(jsonPath("$.data.tokenType").value("Bearer"));

    }

    @Test
    public void testAuthenticationWithInvalidUsername() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/authentication").content(getJsonPayload("invalidUsername", this.password))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.errors[0]").value("Incorrect login and/or password"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    private Source getMockedUser(String username, String password) {
        Source source = new Source(username, new BCryptPasswordEncoder().encode(password), sourceProfileRepository.findById(1L).get());
        source.setId(1L);
        return source;
    }

    private String getJsonPayload(String login, String password) throws JsonProcessingException {
        LoginForm loginForm = new LoginForm();
        loginForm.setUsername(login);
        loginForm.setPassword(password);

        return new ObjectMapper().writeValueAsString(loginForm);
    }

}



















