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
import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class LogEventServiceTest {

    private LogEvent logEvent;

    @MockBean
    private LogEventRepository mockedLogEventRepository;

    @Autowired
    LogEventService logEventService;

    @Before
    public void setUp() {
        User user = new User();
        user.setId(1L);

        this.logEvent = new LogEvent(Level.ERROR, "Test description", "Test log", user, LocalDateTime.now(), 5);
        this.logEvent.setId(1L);
    }

    @Test
    @WithMockUser
    public void testSaveLogEventService() {
        BDDMockito.given(mockedLogEventRepository.save(Mockito.any(LogEvent.class))).willReturn(this.logEvent);

        LogEvent logEvent = logEventService.save(new LogEvent());

        Assert.assertNotNull(logEvent);
        Assert.assertEquals(logEvent.getId(), this.logEvent.getId());
        Assert.assertEquals(logEvent.getLevel(), this.logEvent.getLevel());
        Assert.assertEquals(logEvent.getDescription(), this.logEvent.getDescription());
        Assert.assertEquals(logEvent.getLog(), this.logEvent.getLog());
        Assert.assertEquals(logEvent.getSource().getId(), this.logEvent.getSource().getId());
        Assert.assertEquals(logEvent.getDate(), this.logEvent.getDate());
        Assert.assertEquals(logEvent.getQuantity(), this.logEvent.getQuantity());
    }

    @Test
    public void testFindByPresentedId() {
        BDDMockito.given(mockedLogEventRepository.findById(Mockito.anyLong())).willReturn(Optional.of(this.logEvent));

        Optional<LogEvent> logEvent = logEventService.findById(1L);

        Assert.assertTrue(logEvent.isPresent());
        Assert.assertEquals(logEvent.get().getId(), this.logEvent.getId());
        Assert.assertEquals(logEvent.get().getLevel(), this.logEvent.getLevel());
        Assert.assertEquals(logEvent.get().getDescription(), this.logEvent.getDescription());
        Assert.assertEquals(logEvent.get().getLog(), this.logEvent.getLog());
        Assert.assertEquals(logEvent.get().getSource().getId(), this.logEvent.getSource().getId());
        Assert.assertEquals(logEvent.get().getDate(), this.logEvent.getDate());
        Assert.assertEquals(logEvent.get().getQuantity(), this.logEvent.getQuantity());
    }

    @Test
    public void testFindByUnpresentedId() {
        BDDMockito.given(mockedLogEventRepository.findById(Mockito.anyLong())).willReturn(Optional.empty());
        Optional<LogEvent> logEvent = logEventService.findById(1L);
        Assert.assertTrue(!logEvent.isPresent());
    }

}
