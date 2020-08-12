package com.api.error.center.repository;

import com.api.error.center.entity.UserProfile;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UserProfileRepositoryTest {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Before
    public void setUp() {
        UserProfile userProfile = new UserProfile();
        userProfile.setProfileName("mockedProfile");
        UserProfile response = userProfileRepository.save(userProfile);
    }

    @Test
    public void testSave() {
        UserProfile userProfile = new UserProfile();
        userProfile.setProfileName("test profile");
        UserProfile response = userProfileRepository.save(userProfile);

        Assert.assertNotNull(response);
        Assert.assertEquals(response.getProfileName(), userProfile.getProfileName());
    }

    @Test
    public void testFindById() {
        Optional<UserProfile> userProfile = userProfileRepository.findById(1L);
        Assert.assertNotNull(userProfile);
        Assert.assertEquals(new Long(1), userProfile.get().getId());
        Assert.assertEquals("mockedProfile", userProfile.get().getAuthority());
    }

    @Test
    public void testFindByProfileName() {
        Optional<UserProfile> userProfile = userProfileRepository.findByProfileName("mockedProfile");
        Assert.assertNotNull(userProfile);
        Assert.assertEquals(new Long(1), userProfile.get().getId());
        Assert.assertEquals("mockedProfile", userProfile.get().getAuthority());
    }

}
