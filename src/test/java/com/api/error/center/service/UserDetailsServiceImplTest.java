package com.api.error.center.service;

import com.api.error.center.entity.User;
import com.api.error.center.repository.UserRepository;
import com.api.error.center.service.impl.UserDetailsServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UserDetailsServiceImplTest {

    private User user;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Before
    public void setUp() {
        User user = new User();
        user.setUsername("admin");
        userRepository.save(user);
        this.user = user;
    }

    @Test
    @WithMockUser
    public void testLoadUserByUsernameValid() {
        BDDMockito.given(userRepository.findByUsername(Mockito.anyString())).willReturn(Optional.of(user));
        Optional<User> response = userRepository.findByUsername("admin");

        Assert.assertEquals(response.get(), user);
    }

    @Test(expected = UsernameNotFoundException.class)
    @WithMockUser
    public void testLoadUserInvalidUsername() {
        BDDMockito.given(userRepository.findByUsername(Mockito.anyString())).willReturn(Optional.ofNullable(null));
        userDetailsServiceImpl.loadUserByUsername("invalidUsername");
    }

}
