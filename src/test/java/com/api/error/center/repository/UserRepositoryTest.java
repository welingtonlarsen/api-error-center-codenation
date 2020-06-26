package com.api.error.center.repository;

import com.api.error.center.entity.User;
import com.api.error.center.entity.UserProfile;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UserRepositoryTest {

    private UserProfile userProfile;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Before
    public void setUp() {
        UserProfile userProfile = new UserProfile();
        userProfile.setProfileName("profile test");
        userProfileRepository.save(userProfile);

        this.userProfile = userProfile;
    }

    @Test
    @WithMockUser
    public void testSave() {
        List<UserProfile> usersProfile = new ArrayList<>();
        usersProfile.add(this.userProfile);

        User user = new User();
        user.setPassword("admin");
        user.setUsername("admin");
        user.setUserProfiles(usersProfile);

        User response = userRepository.save(user);

        Assert.assertNotNull(response);
    }

    @Test
    @WithMockUser
    public void testFindByUsername() {
        User user = new User();
        user.setPassword("admin");
        user.setUsername("admin");
        userRepository.save(user);

        Optional<User> response = userRepository.findByUsername("admin");

        Assert.assertTrue(response.isPresent());
        Assert.assertEquals(response.get().getUsername(), user.getUsername());
        Assert.assertEquals(response.get().getPassword(), user.getPassword());
    }
}







