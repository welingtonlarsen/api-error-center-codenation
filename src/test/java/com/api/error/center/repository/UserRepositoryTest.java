package com.api.error.center.repository;

import com.api.error.center.entity.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UserRepositoryTest {

    private User user;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Before
    public void setUp() {
        this.user = new User();
        user.setPassword("admin");
        user.setUsername("admin");
    }

    @Test
    @WithMockUser
    public void testSave() {
        User response = userRepository.save(this.user);
        Assert.assertNotNull(response);
    }

    @Test
    @WithMockUser
    public void testFindByUsername() {
        User userToFind = new User();
        user.setPassword("userToFind");
        user.setUsername("userToFind");
        userRepository.save(userToFind);
        Optional<User> response = userRepository.findByUsername(userToFind.getUsername());

        Assert.assertTrue(response.isPresent());
        Assert.assertEquals(response.get().getUsername(), userToFind.getUsername());
        Assert.assertEquals(response.get().getPassword(), userToFind.getPassword());
    }
}







