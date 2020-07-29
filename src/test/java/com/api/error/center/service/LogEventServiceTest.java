package com.api.error.center.service;

import com.api.error.center.entity.LogEvent;
import com.api.error.center.entity.User;
import com.api.error.center.enums.Level;
import com.api.error.center.repository.LogEventRepository;
import com.api.error.center.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class LogEventServiceTest {

    private LogEvent logEvent;

    @MockBean
    private LogEventRepository mockedLogEventRepository;

    @Autowired
    LogEventService logEventService;

    @Autowired
    UserService userService;

    @Before
    public void setUp() {
        User user = new User();
        userService.save(user);

        this.logEvent = new LogEvent();
        logEvent.setId(1L);
        logEvent.setLevel(Level.ERROR);
        logEvent.setDescription("Test save LogEventRepository");
        logEvent.setSource(user);
        logEvent.setDate(LocalDateTime.now());
        logEvent.setQuantity(5);
    }

    @Test
    @WithMockUser
    public void testSaveLogEventService() {
        BDDMockito.given(mockedLogEventRepository.save(Mockito.any(LogEvent.class))).willReturn(this.logEvent);

        LogEvent logEvent = logEventService.save(new LogEvent());

        Assert.assertNotNull(logEvent);
        Assert.assertEquals(logEvent.getId(), logEvent.getId());
        Assert.assertEquals(logEvent.getLevel(), logEvent.getLevel());
        Assert.assertEquals(logEvent.getDescription(), logEvent.getDescription());
        Assert.assertEquals(logEvent.getSource().getId(), logEvent.getSource().getId());
        Assert.assertEquals(logEvent.getDate(), logEvent.getDate());
        Assert.assertEquals(logEvent.getQuantity(), logEvent.getQuantity());
    }

}
