package com.api.error.center.repository;

import com.api.error.center.entity.UserProfile;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UserProfileRepositoryTest {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Test
    public void testSave() {
        UserProfile userProfile = new UserProfile();
        userProfile.setProfileName("test profile");
        UserProfile response = userProfileRepository.save(userProfile);

        Assert.assertNotNull(response);
        Assert.assertEquals(response.getProfileName(), userProfile.getProfileName());
    }

}
