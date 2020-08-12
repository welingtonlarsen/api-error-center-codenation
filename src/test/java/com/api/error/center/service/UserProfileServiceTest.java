package com.api.error.center.service;

import com.api.error.center.entity.UserProfile;
import com.api.error.center.repository.UserProfileRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UserProfileServiceTest {

    @MockBean
    UserProfileRepository mockedUserProfileRepository;

    @Autowired
    UserProfileService userProfileService;

    @Before
    public void setUp() {

    }

    @Test
    public void testSave() {
        BDDMockito.given(mockedUserProfileRepository.save(Mockito.any(UserProfile.class))).willReturn(getMockedUserProfile());
        UserProfile userProfile = userProfileService.save(new UserProfile());
        assertNotNull(userProfile);;
        assertEquals(new Long(1), userProfile.getId());
        assertEquals("mockedUserProfile", userProfile.getProfileName());
    }

    @Test
    public void testFindById() {
        BDDMockito.given(mockedUserProfileRepository.findById(Mockito.anyLong())).willReturn(Optional.of(getMockedUserProfile()));
        Optional<UserProfile> userProfile = userProfileService.findById(1L);
        assertTrue(userProfile.isPresent());
        assertEquals(new Long(1), userProfile.get().getId());
        assertEquals("mockedUserProfile", userProfile.get().getProfileName());
    }

    @Test
    public void testFindByProfileName() {
        BDDMockito.given(mockedUserProfileRepository.findByProfileName(Mockito.anyString())).willReturn(Optional.of(getMockedUserProfile()));
        Optional<UserProfile> userProfile = userProfileService.findByProfileName("mockedUserProfile");
        assertTrue(userProfile.isPresent());
        assertEquals(new Long(1), userProfile.get().getId());
        assertEquals("mockedUserProfile", userProfile.get().getProfileName());
    }

    public UserProfile getMockedUserProfile() {
        UserProfile userProfile= new UserProfile();
        userProfile.setId(1L);
        userProfile.setProfileName("mockedUserProfile");
        return userProfile;
    }

}
