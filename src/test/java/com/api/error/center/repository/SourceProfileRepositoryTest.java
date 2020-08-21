package com.api.error.center.repository;

import com.api.error.center.entity.SourceProfile;
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
public class SourceProfileRepositoryTest {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Before
    public void setUp() {
        SourceProfile sourceProfile = new SourceProfile();
        sourceProfile.setProfileName("mockedProfile");
        SourceProfile response = userProfileRepository.save(sourceProfile);
    }

    @Test
    public void testSave() {
        SourceProfile sourceProfile = new SourceProfile();
        sourceProfile.setProfileName("test profile");
        SourceProfile response = userProfileRepository.save(sourceProfile);

        Assert.assertNotNull(response);
        Assert.assertEquals(response.getProfileName(), sourceProfile.getProfileName());
    }

    @Test
    public void testFindById() {
        Optional<SourceProfile> userProfile = userProfileRepository.findById(1L);
        Assert.assertNotNull(userProfile);
        Assert.assertEquals(new Long(1), userProfile.get().getId());
        Assert.assertEquals("mockedProfile", userProfile.get().getAuthority());
    }

    @Test
    public void testFindByProfileName() {
        Optional<SourceProfile> userProfile = userProfileRepository.findByProfileName("mockedProfile");
        Assert.assertNotNull(userProfile);
        Assert.assertEquals(new Long(1), userProfile.get().getId());
        Assert.assertEquals("mockedProfile", userProfile.get().getAuthority());
    }

}
