package com.api.error.center.controller;

import com.api.error.center.entity.LogEvent;
import com.api.error.center.entity.Source;
import com.api.error.center.enums.Level;
import com.api.error.center.form.LogEventForm;
import com.api.error.center.repository.SourceRepository;
import com.api.error.center.service.LogEventService;
import com.api.error.center.util.LogEventTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class LogEventControllerTest extends LogEventTest {

    private static LogEvent mockedLogEventA;
    private static LogEvent mockedLogEventB;
    private static LogEvent mockedLogEventC;
    private static LogEvent mockedLogEventD;

    @MockBean
    LogEventService logEventService;

    @MockBean
    SourceRepository sourceRepository;

    @Autowired
    MockMvc mockMvc;

    @BeforeAll
    public static void setUp() {
        Source sourceA = new Source();
        Source sourceB = new Source();
        sourceA.setId(1L);
        sourceB.setId(2L);

        mockedLogEventA = new LogEvent(Level.ERROR, DESCRIPTION_A, LOG_A, sourceA, DATE_A, 5);
        mockedLogEventA.setId(1L);
        mockedLogEventA = new LogEvent(Level.ERROR, DESCRIPTION_A, LOG_A, sourceA, DATE_A, 55);
        mockedLogEventA.setId(2L);
        mockedLogEventB = new LogEvent(Level.WARNING, DESCRIPTION_B, LOG_B, sourceB, DATE_B, 5);
        mockedLogEventB.setId(3L);
        mockedLogEventC = new LogEvent(Level.INFO, DESCRIPTION_C, LOG_C, sourceB, DATE_C, 10);
        mockedLogEventC.setId(4L);
        mockedLogEventD = new LogEvent(Level.WARNING, DESCRIPTION_C, LOG_C, sourceB, DATE_C, 8);
        mockedLogEventD.setId(4L);
    }

    @Test
    @WithMockUser
    public void testCreateValidLogEvent() throws Exception {
        BDDMockito.given(logEventService.save(any(LogEvent.class))).willReturn(getMockedLogEvent());

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
        BDDMockito.given(logEventService.findById(anyLong())).willReturn(Optional.of(getMockedLogEvent()));

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
        BDDMockito.given(logEventService.findById(anyLong())).willReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/log/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.errors").isEmpty());
    }

    @Test
    @WithMockUser
    public void testFindAllUsingLevelFilter() throws Exception {
        BDDMockito.given(logEventService.findAllByFilters(any(Level.class), nullable(String.class), nullable(String.class), nullable(Source.class), nullable(LocalDateTime.class), nullable(LocalDateTime.class),
                nullable(Integer.class), any(PageRequest.class))).willReturn(getLogEventMockedPage());

        mockMvc.perform(MockMvcRequestBuilders.get("/log").param("level", "ERROR")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content").isNotEmpty())
                .andExpect(jsonPath("$.data.pageable").isNotEmpty())
                .andExpect(jsonPath("$.errors").isEmpty());
    }

    @Test
    @WithMockUser
    public void testFindAllUsingDescriptionFilter() throws Exception {
        BDDMockito.given(logEventService.findAllByFilters(nullable(Level.class), any(String.class), nullable(String.class), nullable(Source.class), nullable(LocalDateTime.class), nullable(LocalDateTime.class),
                nullable(Integer.class), any(PageRequest.class))).willReturn(getLogEventMockedPage());

        mockMvc.perform(MockMvcRequestBuilders.get("/log").param("description", "someDescription")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content").isNotEmpty())
                .andExpect(jsonPath("$.data.pageable").isNotEmpty())
                .andExpect(jsonPath("$.errors").isEmpty());
    }

    @Test
    @WithMockUser
    public void testFindAllUsingLogFilter() throws Exception {
        BDDMockito.given(logEventService.findAllByFilters(nullable(Level.class), nullable(String.class), any(String.class), nullable(Source.class), nullable(LocalDateTime.class), nullable(LocalDateTime.class),
                nullable(Integer.class), any(PageRequest.class))).willReturn(getLogEventMockedPage());

        mockMvc.perform(MockMvcRequestBuilders.get("/log").param("log", "someLog")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content").isNotEmpty())
                .andExpect(jsonPath("$.data.pageable").isNotEmpty())
                .andExpect(jsonPath("$.errors").isEmpty());
    }

    @Test
    @WithMockUser
    public void testFindAllUsingSourceIdFilter() throws Exception {
        BDDMockito.given(logEventService.findAllByFilters(nullable(Level.class), nullable(String.class), nullable(String.class), any(Source.class), nullable(LocalDateTime.class), nullable(LocalDateTime.class),
                nullable(Integer.class), any(PageRequest.class))).willReturn(getLogEventMockedPage());

        Source source = new Source();
        source.setId(1L);
        BDDMockito.given(sourceRepository.findById(anyLong())).willReturn(Optional.of(source));

        mockMvc.perform(MockMvcRequestBuilders.get("/log").param("sourceId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content").isNotEmpty())
                .andExpect(jsonPath("$.data.pageable").isNotEmpty())
                .andExpect(jsonPath("$.errors").isEmpty());
    }

    private LogEvent getMockedLogEvent() {
        Source source = new Source();
        source.setId(1L);
        LogEvent logEvent = new LogEvent(Level.ERROR, "Test description", "Teste log", source, LocalDateTime.now(), 5);
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

    private Page<LogEvent> getLogEventMockedPage() {
        List<LogEvent> logEvents = Arrays.asList(mockedLogEventA, mockedLogEventB, mockedLogEventC, mockedLogEventD);
        Page<LogEvent> logEventsPage = new PageImpl<>(logEvents, pageable, logEvents.size());
        return logEventsPage;
    }


}
