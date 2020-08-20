package com.api.error.center.controller;

import com.api.error.center.entity.LogEvent;
import com.api.error.center.entity.User;
import com.api.error.center.enums.Level;
import com.api.error.center.form.LogEventForm;
import com.api.error.center.service.LogEventService;
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

import java.time.LocalDateTime;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class LogEventControllerTest {

    @MockBean
    LogEventService logEventService;

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser
    public void testCreateValidLogEvent() throws Exception {
        BDDMockito.given(logEventService.save(Mockito.any(LogEvent.class))).willReturn(getMockedLogEvent());

        mockMvc.perform(MockMvcRequestBuilders.post("/log").content(getValidLogEventPayLoad())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").isNotEmpty())
                .andExpect(jsonPath("$.data.level").isNotEmpty())
                .andExpect(jsonPath("$.data.description").isNotEmpty())
                .andExpect(jsonPath("$.data.log").isNotEmpty())
                .andExpect(jsonPath("$.data.sourceId").isNotEmpty())
                .andExpect(jsonPath("$.data.date").isNotEmpty())
                .andExpect(jsonPath("$.data.quantity").isNotEmpty())
                .andExpect(jsonPath("$.errors").isEmpty());
    }

    @Test
    @WithMockUser
    public void testCreateInvalidLogEvent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/log").content(getInvalidLogEventPayLoad())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isNotEmpty())
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    @WithMockUser
    public void testFindByPresentedId() throws Exception {
        BDDMockito.given(logEventService.findById(Mockito.anyLong())).willReturn(Optional.of(getMockedLogEvent()));

        mockMvc.perform(MockMvcRequestBuilders.get("/log/1")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.errors").isEmpty());
    }

    @Test
    @WithMockUser
    public void testFindByUnpresentedId() throws Exception {
        BDDMockito.given(logEventService.findById(Mockito.anyLong())).willReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/log/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.errors").isEmpty());
    }

    private LogEvent getMockedLogEvent() {
        User user = new User();
        user.setId(1L);
        LogEvent logEvent = new LogEvent(Level.ERROR, "Test description", "Teste log", user, LocalDateTime.now(), 5);
        logEvent.setId(1L);

        return logEvent;
    }

    private String getValidLogEventPayLoad() throws JsonProcessingException {
        LogEventForm logEventForm = new LogEventForm();
        logEventForm.setLevel("ERROR");
        logEventForm.setDescription("Test description");
        logEventForm.setLog("Teste log");
        logEventForm.setDate("01-05-2020 10:30:15");
        logEventForm.setQuantity(10);

        return new ObjectMapper().writeValueAsString(logEventForm);
    }

    private String getInvalidLogEventPayLoad() throws JsonProcessingException {
        LogEventForm logEventForm = new LogEventForm();
        logEventForm.setDescription("Test description");
        logEventForm.setLog("Teste log");
        logEventForm.setDate("01-05-2020 10:30:15");
        logEventForm.setQuantity(10);

        return new ObjectMapper().writeValueAsString(logEventForm);
    }
}
