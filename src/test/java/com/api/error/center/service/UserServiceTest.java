package com.api.error.center.service;

import com.api.error.center.entity.User;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UserServiceTest {

    private User user;

    @MockBean
    private UserRepository MockedUserRepository;

    @Autowired
    private UserService userService;

    @Before
    public void setUp() {
        this.user = new User();
        user.setUsername("admin");
    }

    @Test
    @WithMockUser
    public void testFindByUsername() {
        BDDMockito.given(MockedUserRepository.findByUsername(Mockito.anyString())).willReturn(Optional.of(this.user));

        Optional<User> response = userService.findByUsername("admin");
        Assert.assertTrue(response.isPresent());
        Assert.assertEquals("admin", response.get().getUsername());
    }

    @Test
    @WithMockUser
    public void testLoadUserByValidUsername() {
        BDDMockito.given(MockedUserRepository.findByUsername(Mockito.anyString())).willReturn(Optional.of(this.user));
        UserDetails response = this.userService.loadUserByUsername(user.getUsername());

        Assert.assertEquals(response.getUsername(), this.user.getUsername());
    }

    @Test(expected = UsernameNotFoundException.class)
    @WithMockUser
    public void testLoadUserInvalidUsername() {
        BDDMockito.given(MockedUserRepository.findByUsername(Mockito.anyString())).willReturn(Optional.ofNullable(null));
        this.userService.loadUserByUsername("invalidUsername");
    }



}
