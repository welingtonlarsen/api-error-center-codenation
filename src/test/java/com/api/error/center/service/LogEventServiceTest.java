package com.api.error.center.service;

import com.api.error.center.entity.LogEvent;
import com.api.error.center.entity.User;
import com.api.error.center.enums.Level;
import com.api.error.center.repository.LogEventRepository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.api.error.center.util.LogEventUtil.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class LogEventServiceTest {

    /*
    private static LogEvent mockedLogEventA;
    private static LogEvent mockedLogEventB;
    private static LogEvent mockedLogEventC;
    private static LogEvent mockedLogEventD;

    @MockBean
    private LogEventRepository logEventRepository;

    @Autowired
    LogEventService logEventService;

    @BeforeAll
    public static void setUp() {
        User userA = new User();
        User userB = new User();
        userA.setId(1L);
        userB.setId(2L);

        mockedLogEventA = new LogEvent(Level.ERROR, DESCRIPTION_A, LOG_A, userA, DATE_A, 5);
        mockedLogEventA.setId(1L);
        mockedLogEventA = new LogEvent(Level.ERROR, DESCRIPTION_A, LOG_A, userA, DATE_A, 55);
        mockedLogEventA.setId(2L);
        mockedLogEventB = new LogEvent(Level.WARNING, DESCRIPTION_B, LOG_B, userB, DATE_B, 5);
        mockedLogEventB.setId(3L);
        mockedLogEventC = new LogEvent(Level.INFO, DESCRIPTION_C, LOG_C, userB, DATE_C, 10);
        mockedLogEventC.setId(4L);
        mockedLogEventD = new LogEvent(Level.WARNING, DESCRIPTION_C, LOG_C, userB, DATE_C, 8);
        mockedLogEventD.setId(4L);
    }

    @Test
    @WithMockUser
    public void testSaveLogEventService() throws JsonProcessingException {
        BDDMockito.given(logEventRepository.save(Mockito.any(LogEvent.class))).willReturn(mockedLogEventA);
        LogEvent logEvent = logEventService.save(new LogEvent());
        assertEquals(transformLogEventInJson(mockedLogEventA), transformLogEventInJson(logEvent));
    }

    @Test
    public void testFindByPresentedId() throws JsonProcessingException {
        BDDMockito.given(logEventRepository.findById(Mockito.anyLong())).willReturn(Optional.of(mockedLogEventA));
        Optional<LogEvent> logEvent = logEventService.findById(1L);
        assertTrue(logEvent.isPresent());
        assertEquals(transformLogEventInJson(mockedLogEventA), transformLogEventInJson(logEvent.get()));
    }

    @Test
    public void testFindByUnpresentedId() {
        BDDMockito.given(logEventRepository.findById(Mockito.anyLong())).willReturn(Optional.empty());
        Optional<LogEvent> logEvent = logEventService.findById(1L);
        assertTrue(!logEvent.isPresent());
    }

    @Test
    public void testFindAllBetweenDates() {
        BDDMockito.given(logEventRepository.findAllByFilters(null, null, null, null, DATE_A, DATE_B, null))
                .willReturn(getLogEventMockedList());
        List<LogEvent> logEvents = logEventService.findAllByFilters(null, null, null, null, DATE_A, DATE_B, null);
        assertEquals(mockedLogEventA, logEvents.get(0));
        assertEquals(mockedLogEventB, logEvents.get(1));
        assertEquals(mockedLogEventC, logEvents.get(2));
        assertEquals(mockedLogEventD, logEvents.get(3));
    }

    @Test
    public void testFindAllByLevel() {
        BDDMockito.given(logEventRepository.findAllByFilters(Level.ERROR, null, null, null, null, null, null))
                .willReturn(getLogEventMockedList());
        List<LogEvent> logEvents = logEventService.findAllByFilters(Level.ERROR, null, null, null, null, null, null);
        assertEquals(mockedLogEventA, logEvents.get(0));
        assertEquals(mockedLogEventB, logEvents.get(1));
        assertEquals(mockedLogEventC, logEvents.get(2));
        assertEquals(mockedLogEventD, logEvents.get(3));
    }

    @Test
    public void testFindAllBySource() {
        User source = new User();
        source.setId(1L);
        BDDMockito.given(logEventRepository.findAllByFilters(null, null, null, source, null, null, null))
                .willReturn(getLogEventMockedList());
        List<LogEvent> logEvents = logEventService.findAllByFilters(null, null, null, source, null, null, null);
        assertEquals(mockedLogEventA, logEvents.get(0));
        assertEquals(mockedLogEventB, logEvents.get(1));
        assertEquals(mockedLogEventC, logEvents.get(2));
        assertEquals(mockedLogEventD, logEvents.get(3));
    }

    @Test
    public void testFindAllByQuantity() {
        BDDMockito.given(logEventRepository.findAllByFilters(null, null, null, null, null, null, 10))
                .willReturn(getLogEventMockedList());
        List<LogEvent> logEvents = logEventService.findAllByFilters(null, null, null, null, null, null, 10);
        assertEquals(mockedLogEventA, logEvents.get(0));
        assertEquals(mockedLogEventB, logEvents.get(1));
        assertEquals(mockedLogEventC, logEvents.get(2));
        assertEquals(mockedLogEventD, logEvents.get(3));
    }

    @Test
    public void testFindAllByNullsFilters() {
        BDDMockito.given(logEventRepository.findAllByFilters(null, null, null, null, null, null, null))
                .willReturn(getLogEventMockedList());
        List<LogEvent> logEvents = logEventService.findAllByFilters(null, null, null, null, null, null, null);
        assertEquals(mockedLogEventA, logEvents.get(0));
        assertEquals(mockedLogEventB, logEvents.get(1));
        assertEquals(mockedLogEventC, logEvents.get(2));
        assertEquals(mockedLogEventD, logEvents.get(3));
    }

    private List<LogEvent> getLogEventMockedList() {
        List<LogEvent> logEvents = new ArrayList<>();
        logEvents.add(mockedLogEventA);
        logEvents.add(mockedLogEventB);
        logEvents.add(mockedLogEventC);
        logEvents.add(mockedLogEventD);
        return logEvents;
    }

    private String transformLogEventInJson(LogEvent logEvent) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(logEvent);
    }

     */
}
