package com.api.error.center.service;

import com.api.error.center.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class TokenServiceTest {

    @MockBean
    Authentication authentication;

    @Autowired
    TokenService tokenService;

    @Test
    public void testGenerateToken() {
        User user = new User();
        user.setId(1L);
        user.setUsername("username");
        user.setPassword("password");

        BDDMockito.given(authentication.getPrincipal()).willReturn(user);
        String token = tokenService.generateToken(this.authentication);

        Assert.assertNotNull(token);
        Assert.assertTrue(tokenService.validToken(token));
        Assert.assertEquals(tokenService.getUserIdFromToken(token), user.getId());
    }
}
