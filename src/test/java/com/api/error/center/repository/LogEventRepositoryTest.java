package com.api.error.center.repository;

import com.api.error.center.entity.LogEvent;
import com.api.error.center.entity.User;
import com.api.error.center.enums.Level;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class LogEventRepositoryTest {

    private User user;

    @Autowired
    private LogEventRepository logEventRepository;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() {
        User user = new User();
        user.setUsername("userForTest");
        user.setPassword("passwordForTest");
        this.user = userRepository.save(user);
    }

    @Test
    @WithMockUser
    public void testSave() {
        LogEvent logEvent = new LogEvent(Level.ERROR, "Test save LogEventRepository", "Test save Log", this.user, LocalDateTime.now(), 5);
        LogEvent logEventSaved = logEventRepository.save(logEvent);

        Assert.assertNotNull(logEventSaved);
        Assert.assertEquals(logEventSaved.getId(), logEvent.getId());
        Assert.assertEquals(logEventSaved.getLevel(), logEvent.getLevel());
        Assert.assertEquals(logEventSaved.getDescription(), logEvent.getDescription());
        Assert.assertEquals(logEventSaved.getSource().getId(), logEvent.getSource().getId());
        Assert.assertEquals(logEventSaved.getDate(), logEvent.getDate());
        Assert.assertEquals(logEventSaved.getQuantity(), logEvent.getQuantity());
    }

    @Test(expected = ConstraintViolationException.class)
    @WithMockUser
    public void testSaveIncompletedLogEvent() {
        LogEvent logEvent = new LogEvent();
        logEventRepository.save(logEvent);
    }
}
