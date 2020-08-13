package com.api.error.center.repository;

import com.api.error.center.entity.LogEvent;
import com.api.error.center.entity.User;
import com.api.error.center.enums.Level;

import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class LogEventRepositoryTest {

    private static final String DESCRIPTION_1 = "Description 1";
    private static final String DESCRIPTION_2 = "Description 2";
    private static final String DESCRIPTION_3 = "Description 3";

    private static final String LOG_1 = "Log 1";
    private static final String LOG_2 = "Log 2";
    private static final String LOG_3 = "Log 3";

    private static final LocalDateTime DATE_1 = LocalDateTime.of(2020, 8, 5, 15, 30, 50);
    private static final LocalDateTime DATE_2 = LocalDateTime.of(2020, 6, 25, 9, 50, 15);
    private static final LocalDateTime DATE_3 = LocalDateTime.of(2020, 8, 5, 17, 35, 40);
    private static final LocalDateTime DATE_4 = LocalDateTime.of(2020, 5, 3, 15, 30, 50);

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

        logEventRepository.save(new LogEvent(Level.ERROR, DESCRIPTION_1, LOG_1, user, DATE_1, 5));
        logEventRepository.save(new LogEvent(Level.WARNING, DESCRIPTION_3, LOG_3, user, DATE_2, 10));
        logEventRepository.save(new LogEvent(Level.ERROR, DESCRIPTION_1, LOG_2, user, DATE_3, 5));
        logEventRepository.save(new LogEvent(Level.INFO, DESCRIPTION_2, LOG_3, user, DATE_4, 10));
    }

    @AfterEach

    @Test
    public void testSave() {
        Optional<User> user = userRepository.findById(1L);

        LogEvent logEvent = new LogEvent(Level.ERROR, DESCRIPTION_1, LOG_1, user.get(), LocalDateTime.now(), 5);
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

        List<LogEvent> logEvents = logEventRepository.findAllByFilters(null, null, null, null, startDate, endDate, null);
        assertEquals(2, logEvents.size());
    }

    @Test
    public void testFindAllByLevel() {
        List<LogEvent> logEvents = logEventRepository.findAllByFilters(Level.ERROR, null, null, null, null, null, null);
        assertEquals(2, logEvents.size());
    }

    @Test
    public void testFindAllByDescription() {
        List<LogEvent> logEvents = logEventRepository.findAllByFilters(null, DESCRIPTION_1, null, null, null, null, null);
        assertEquals(2, logEvents.size());
    }

    @Test
    public void testFindAllByLog() {
        List<LogEvent> logEvents = logEventRepository.findAllByFilters(null, null, LOG_1, null, null, null, null);
        assertEquals(1, logEvents.size());
    }

    @Test
    public void testFindAllBySource() {
        Optional<User> user = userRepository.findById(1L);
        List<LogEvent> logEvents = logEventRepository.findAllByFilters(null, null, null, user.get(), null, null, null);
        assertEquals(4, logEvents.size());
    }

    @Test
    public void testFindAllByQuantity() {
        List<LogEvent> logEvents = logEventRepository.findAllByFilters(null, null, null, null, null, null, 10);
        assertEquals(2, logEvents.size());
    }

    @Test
    public void testFindAllByNullsFilters() {
        List<LogEvent> logEvents = logEventRepository.findAllByFilters(null, null, null, null, null, null, null);
        assertEquals(4, logEvents.size());
    }
}
