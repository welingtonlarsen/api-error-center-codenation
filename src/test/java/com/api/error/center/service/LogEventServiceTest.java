package com.api.error.center.service;

import com.api.error.center.entity.LogEvent;
import com.api.error.center.entity.User;
import com.api.error.center.enums.Level;
import com.api.error.center.repository.LogEventRepository;

import com.api.error.center.util.LogEventTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.api.error.center.util.LogEventTest.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ActiveProfiles("test")
public class LogEventServiceTest extends LogEventTest {

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
        BDDMockito.given(logEventRepository.findAllByFilters(null, null, null, null, DATE_A, DATE_B, null, pageable))
                .willReturn(getLogEventMockedPage());
        Page<LogEvent> logEvents = logEventService.findAllByFilters(null, null, null, null, DATE_A, DATE_B, null, pageable);
        assertEquals(4, logEvents.getTotalElements());
    }

    @Test
    public void testFindAllByLevel() {
        Mockito.doReturn(getLogEventMockedPage()).when(logEventRepository).findAllByFilters(Level.ERROR, null, null, null, startDate, endDate, null, pageable);
        Page<LogEvent> logEvents = logEventService.findAllByFilters(Level.ERROR, null, null, null, null, null, null, pageable);
        assertEquals(4, logEvents.getTotalElements());
    }

    @Test
    public void testFindAllBySource() {
        User source = new User();
        source.setId(1L);
        Mockito.doReturn(getLogEventMockedPage()).when(logEventRepository).findAllByFilters(null, null, null, source, startDate, endDate, null, pageable);
        Page<LogEvent> logEvents = logEventService.findAllByFilters(null, null, null, source, null, null, null, pageable);
        assertEquals(4, logEvents.getTotalElements());
    }

    @Test
    public void testFindAllByQuantity() {
        Mockito.doReturn(getLogEventMockedPage()).when(logEventRepository).findAllByFilters(null, null, null, null, startDate, endDate, 10, pageable);
        Page<LogEvent> logEvents = logEventService.findAllByFilters(null, null, null, null, null, null, 10, pageable);
        assertEquals(4, logEvents.getTotalElements());
    }

    @Test
    public void testFindAllByNullsFilters() {
        Mockito.doReturn(getLogEventMockedPage()).when(logEventRepository).findAllByFilters(null, null, null, null, startDate, endDate, null, pageable);
        Page<LogEvent> logEvents = logEventService.findAllByFilters(null, null, null, null, null, null, null, pageable);
        assertEquals(4, logEvents.getTotalElements());
    }

    private Page<LogEvent> getLogEventMockedPage() {
        List<LogEvent> logEvents = Arrays.asList(mockedLogEventA, mockedLogEventB, mockedLogEventC, mockedLogEventD);
        Page<LogEvent> logEventsPage = new PageImpl<>(logEvents, pageable, logEvents.size());
        return logEventsPage;
    }

    private String transformLogEventInJson(LogEvent logEvent) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(logEvent);
    }

}
