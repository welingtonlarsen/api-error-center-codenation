package com.api.error.center.repository;

import com.api.error.center.entity.LogEvent;
import com.api.error.center.entity.User;
import com.api.error.center.enums.Level;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.api.error.center.util.LogEventUtil.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class LogEventRepositoryTest {

    @Autowired
    private LogEventRepository logEventRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        User user = new User();
        user.setUsername("User 1");
        user.setPassword("Password 1");
        userRepository.save(user);

        logEventRepository.save(new LogEvent(Level.ERROR, DESCRIPTION_A, LOG_A, user, DATE_A, 5));
        logEventRepository.save(new LogEvent(Level.WARNING, DESCRIPTION_C, LOG_C, user, DATE_B, 10));
        logEventRepository.save(new LogEvent(Level.ERROR, DESCRIPTION_A, LOG_B, user, DATE_C, 5));
        logEventRepository.save(new LogEvent(Level.INFO, DESCRIPTION_B, LOG_C, user, DATE_D, 10));
    }

    @Test
    public void testSave() {
        Optional<User> user = userRepository.findById(1L);

        LogEvent logEvent = new LogEvent(Level.ERROR, DESCRIPTION_A, LOG_A, user.get(), LocalDateTime.now(), 5);
        LogEvent logEventSaved = logEventRepository.save(logEvent);

        assertNotNull(logEventSaved);
        assertEquals(logEvent.toString(), logEventSaved.toString());
    }

    @Test
    public void testSaveIncompletedLogEvent() {
        assertThrows(ConstraintViolationException.class, () -> {
            LogEvent logEvent = new LogEvent();
            logEventRepository.save(logEvent);
        });
    }

    @Test
    public void testFindByExistentId() {
        Optional<LogEvent> logEvent = logEventRepository.findById(1L);
        assertTrue(logEvent.isPresent());
    }

    @Test
    public void testFindByNoExistentId() {
        Optional<LogEvent> logEvent = logEventRepository.findById(99L);
        assertTrue(!logEvent.isPresent());
    }

    @Test
    public void testFindAllBetweenDates() {
        LocalDateTime startDate = LocalDateTime.of(2020, 8, 5, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 8, 5, 23, 59, 59);

        Page<LogEvent> logEvents = logEventRepository.findAllByFilters(null, null, null, null, startDate, endDate, null, pageable);
        assertEquals(2, logEvents.getTotalElements());
    }

    @Test
    public void testFindAllByLevel() {
        Page<LogEvent> logEvents = logEventRepository.findAllByFilters(Level.ERROR, null, null, null, startDate, endDate, null, pageable);
        assertEquals(2, logEvents.getTotalElements());
    }

    @Test
    public void testFindAllByDescription() {
        Page<LogEvent> logEvents = logEventRepository.findAllByFilters(null, DESCRIPTION_A, null, null, startDate, endDate, null, pageable);
        assertEquals(2, logEvents.getTotalElements());
    }

    @Test
    public void testFindAllByLog() {
        Page<LogEvent> logEvents = logEventRepository.findAllByFilters(null, null, LOG_A, null, startDate, endDate, null, pageable);
        assertEquals(1, logEvents.getTotalElements());
    }

    @Test
    public void testFindAllBySource() {
        Optional<User> user = userRepository.findById(1L);
        Page<LogEvent> logEvents = logEventRepository.findAllByFilters(null, null, null, user.get(), startDate, endDate, null, pageable);
        assertEquals(4, logEvents.getTotalElements());
    }

    @Test
    public void testFindAllByQuantity() {
        Page<LogEvent> logEvents = logEventRepository.findAllByFilters(null, null, null, null, startDate, endDate, 10, pageable);
        assertEquals(2, logEvents.getTotalElements());
    }

    @Test
    public void testFindAllByNullsFilters() {
        Page<LogEvent> logEvents = logEventRepository.findAllByFilters(null, null, null, null, startDate, endDate, null, pageable);
        assertEquals(4, logEvents.getTotalElements());
    }

}
