package com.api.error.center.repository;

import com.api.error.center.entity.LogEvent;
import com.api.error.center.entity.User;
import com.api.error.center.enums.Level;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class LogEventRepositoryTest {

    @Autowired
    private LogEventRepository logEventRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @WithMockUser
    public void testSave() {
        User user = new User();
        user.setUsername("userForTest");
        user.setPassword("passwordForTest");
        userRepository.save(user);

        LogEvent logEvent = new LogEvent();
        logEvent.setLevel(Level.ERROR);
        logEvent.setDescription("Test save LogEventRepository");
        logEvent.setSource(user);
        logEvent.setDate(LocalDateTime.now());
        logEvent.setQuantity(5);
        logEventRepository.save(logEvent);

        Assert.assertNotNull(this.findFirstLogEvent());
        Assert.assertEquals(this.findFirstLogEvent().getId(), logEvent.getId());
        Assert.assertEquals(this.findFirstLogEvent().getLevel(), logEvent.getLevel());
        Assert.assertEquals(this.findFirstLogEvent().getDescription(), logEvent.getDescription());
        Assert.assertEquals(this.findFirstLogEvent().getSource().getId(), logEvent.getSource().getId());
        Assert.assertEquals(this.findFirstLogEvent().getDate(), logEvent.getDate());
        Assert.assertEquals(this.findFirstLogEvent().getQuantity(), logEvent.getQuantity());

    }

    private LogEvent findFirstLogEvent() {
        return logEventRepository.findAll().stream().findFirst().get();
    }

}
