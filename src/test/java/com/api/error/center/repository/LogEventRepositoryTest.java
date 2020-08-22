package com.api.error.center.repository;

import com.api.error.center.entity.LogEvent;
import com.api.error.center.entity.Source;
import com.api.error.center.entity.SourceProfile;
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
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class LogEventRepositoryTest {

    private final static LocalDateTime startDate = LocalDateTime.of(1900, 1, 1, 0, 0, 0);
    private final static LocalDateTime endDate = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59).withNano(0);
    private final static Pageable pageable = PageRequest.of(0, 10);

    @Autowired
    private LogEventRepository logEventRepository;

    @Autowired
    private SourceRepository sourceRepository;

    @Autowired
    private SourceProfileRepository sourceProfileRepository;

    @BeforeEach
    public void setUp() {
        SourceProfile sourceProfile = sourceProfileRepository.save(new SourceProfile("Mocked Source Profile"));
        Source source = sourceRepository.save(new Source("Mocked User", "mockedpassword", sourceProfile));
        logEventRepository.save(new LogEvent(Level.ERROR, "Mocked description A", "Mocked log A", source, LocalDateTime.of(2020, 2, 5, 15, 30, 50), 5));
        logEventRepository.save(new LogEvent(Level.WARNING, "Mocked description B", "Mocked log B", source, LocalDateTime.of(2020, 3, 25, 23, 59, 10), 1));
        logEventRepository.save(new LogEvent(Level.WARNING, "Mocked description C", "Mocked log C", source, LocalDateTime.of(2020, 4, 15, 12, 50, 49), 7));
        logEventRepository.save(new LogEvent(Level.INFO, "Mocked description D", "Mocked log D", source, LocalDateTime.of(2020, 5, 28, 10, 45, 03), 58));
    }

    @Test
    public void testSave() {
        Optional<Source> source = sourceRepository.findById(1L);
        LogEvent logEvent = new LogEvent(Level.ERROR, "Test save description", "Test log description", source.get(), LocalDateTime.of(2019, 12, 01, 10, 10, 0), 50);
        logEvent = logEventRepository.save(logEvent);

        SourceProfile sourceProfile = logEvent.getSource().getSourceProfile();
        Long sourceProfileId = sourceProfile.getId();
        String sourceProfileName = sourceProfile.getAuthority();

        assertNotNull(logEvent);
        assertEquals(1L, sourceProfileId);
        assertEquals("Mocked Source Profile", sourceProfileName);
        assertEquals(5L, logEvent.getId());
        assertEquals(Level.ERROR, logEvent.getLevel());
        assertEquals("Test save description", logEvent.getDescription());
        assertEquals("Test log description", logEvent.getLog());
        assertEquals(source.get(), logEvent.getSource());
        assertEquals(LocalDateTime.of(2019, 12, 01, 10, 10, 0), logEvent.getDate());
        assertEquals(50, logEvent.getQuantity());
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
        assertEquals(1L, logEvent.get().getId());
        assertEquals(Level.ERROR, logEvent.get().getLevel());
        assertEquals("Mocked description A", logEvent.get().getDescription());
        assertEquals("Mocked log A", logEvent.get().getLog());
        assertEquals(sourceRepository.findById(1L).get().getId(), logEvent.get().getSource().getId());
        assertEquals(sourceRepository.findById(1L).get().getUsername(), logEvent.get().getSource().getUsername());
        assertEquals(sourceRepository.findById(1L).get().getPassword(), logEvent.get().getSource().getPassword());
        assertEquals(LocalDateTime.of(2020, 2, 5, 15, 30, 50), logEvent.get().getDate());
        assertEquals(5, logEvent.get().getQuantity());
    }

    @Test
    public void testFindByNoExistentId() {
        Optional<LogEvent> logEvent = logEventRepository.findById(99L);
        assertTrue(!logEvent.isPresent());
    }

    @Test
    public void testFindAllBetweenDates() {
        LocalDateTime startDate = LocalDateTime.of(2020, 2, 5, 0, 0, 1);
        LocalDateTime endDate = LocalDateTime.of(2020, 2, 5, 23, 59, 59);

        Page<LogEvent> logEvents = logEventRepository.findAllByFilters(null, null, null, null, startDate, endDate, null, pageable);
        LogEvent logEvent = logEvents.get().collect(Collectors.toList()).get(0);
        assertEquals(1, logEvents.getTotalElements());
        assertEquals(1L, logEvent.getId());
        assertEquals(Level.ERROR, logEvent.getLevel());
        assertEquals("Mocked description A", logEvent.getDescription());
        assertEquals("Mocked log A", logEvent.getLog());
        assertEquals(1L, logEvent.getSource().getId());
        assertEquals("Mocked User", logEvent.getSource().getUsername());
        assertEquals("mockedpassword", logEvent.getSource().getPassword());
        assertEquals(LocalDateTime.of(2020, 2, 5, 15, 30, 50), logEvent.getDate());
        assertEquals(5, logEvent.getQuantity());
    }

    @Test
    public void testFindAllByLevel() {
        Page<LogEvent> logEvents = logEventRepository.findAllByFilters(Level.ERROR, null, null, null, startDate, endDate, null, pageable);
        LogEvent logEvent = logEvents.get().collect(Collectors.toList()).get(0);
        assertEquals(1, logEvents.getTotalElements());
        assertEquals(1L, logEvent.getId());
        assertEquals(Level.ERROR, logEvent.getLevel());
        assertEquals("Mocked description A", logEvent.getDescription());
        assertEquals("Mocked log A", logEvent.getLog());
        assertEquals(1L, logEvent.getSource().getId());
        assertEquals("Mocked User", logEvent.getSource().getUsername());
        assertEquals("mockedpassword", logEvent.getSource().getPassword());
        assertEquals(LocalDateTime.of(2020, 2, 5, 15, 30, 50), logEvent.getDate());
        assertEquals(5, logEvent.getQuantity());
    }

    @Test
    public void testFindAllByDescription() {
        Page<LogEvent> logEvents = logEventRepository.findAllByFilters(null, "Mocked description C", null, null, startDate, endDate, null, pageable);
        LogEvent logEvent = logEvents.get().collect(Collectors.toList()).get(0);
        assertEquals(1, logEvents.getTotalElements());
        assertEquals(3L, logEvent.getId());
        assertEquals(Level.WARNING, logEvent.getLevel());
        assertEquals("Mocked description C", logEvent.getDescription());
        assertEquals("Mocked log C", logEvent.getLog());
        assertEquals(1L, logEvent.getSource().getId());
        assertEquals("Mocked User", logEvent.getSource().getUsername());
        assertEquals("mockedpassword", logEvent.getSource().getPassword());
        assertEquals(LocalDateTime.of(2020, 4, 15, 12, 50, 49), logEvent.getDate());
        assertEquals(7, logEvent.getQuantity());
    }

    @Test
    public void testFindAllByLog() {
        Page<LogEvent> logEvents = logEventRepository.findAllByFilters(null, null, "Mocked log B", null, startDate, endDate, null, pageable);
        LogEvent logEvent = logEvents.get().collect(Collectors.toList()).get(0);
        assertEquals(1, logEvents.getTotalElements());
        assertEquals(2L, logEvent.getId());
        assertEquals(Level.WARNING, logEvent.getLevel());
        assertEquals("Mocked description B", logEvent.getDescription());
        assertEquals("Mocked log B", logEvent.getLog());
        assertEquals(1L, logEvent.getSource().getId());
        assertEquals("Mocked User", logEvent.getSource().getUsername());
        assertEquals("mockedpassword", logEvent.getSource().getPassword());
        assertEquals(LocalDateTime.of(2020, 3, 25, 23, 59, 10), logEvent.getDate());
        assertEquals(1, logEvent.getQuantity());
    }

    @Test
    public void testFindAllBySource() {
        Optional<Source> user = sourceRepository.findById(1L);
        Page<LogEvent> logEvents = logEventRepository.findAllByFilters(null, null, null, user.get(), startDate, endDate, null, pageable);
        LogEvent logEvent = logEvents.get().collect(Collectors.toList()).get(0);
        assertEquals(4, logEvents.getTotalElements());
        assertEquals(1L, logEvent.getId());
        assertEquals(Level.ERROR, logEvent.getLevel());
        assertEquals("Mocked description A", logEvent.getDescription());
        assertEquals("Mocked log A", logEvent.getLog());
        assertEquals(1L, logEvent.getSource().getId());
        assertEquals("Mocked User", logEvent.getSource().getUsername());
        assertEquals("mockedpassword", logEvent.getSource().getPassword());
        assertEquals(LocalDateTime.of(2020, 2, 5, 15, 30, 50), logEvent.getDate());
        assertEquals(5, logEvent.getQuantity());
    }

    @Test
    public void testFindAllByQuantity() {
        Page<LogEvent> logEvents = logEventRepository.findAllByFilters(null, null, null, null, startDate, endDate, 58, pageable);
        LogEvent logEvent = logEvents.get().collect(Collectors.toList()).get(0);
        assertEquals(1, logEvents.getTotalElements());
        assertEquals(4L, logEvent.getId());
        assertEquals(Level.INFO, logEvent.getLevel());
        assertEquals("Mocked description D", logEvent.getDescription());
        assertEquals("Mocked log D", logEvent.getLog());
        assertEquals(1L, logEvent.getSource().getId());
        assertEquals("Mocked User", logEvent.getSource().getUsername());
        assertEquals("mockedpassword", logEvent.getSource().getPassword());
        assertEquals(LocalDateTime.of(2020, 5, 28, 10, 45, 03), logEvent.getDate());
        assertEquals(58, logEvent.getQuantity());
    }

    @Test
    public void testFindAllByNullsFilters() {
        Page<LogEvent> logEvents = logEventRepository.findAllByFilters(null, null, null, null, startDate, endDate, null, pageable);
        LogEvent logEvent = logEvents.get().collect(Collectors.toList()).get(0);
        assertEquals(4, logEvents.getTotalElements());
        assertEquals(1L, logEvent.getId());
        assertEquals(Level.ERROR, logEvent.getLevel());
        assertEquals("Mocked description A", logEvent.getDescription());
        assertEquals("Mocked log A", logEvent.getLog());
        assertEquals(1L, logEvent.getSource().getId());
        assertEquals("Mocked User", logEvent.getSource().getUsername());
        assertEquals("mockedpassword", logEvent.getSource().getPassword());
        assertEquals(LocalDateTime.of(2020, 2, 5, 15, 30, 50), logEvent.getDate());
        assertEquals(5, logEvent.getQuantity());
    }

}
