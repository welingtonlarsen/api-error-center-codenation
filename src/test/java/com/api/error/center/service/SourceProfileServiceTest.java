package com.api.error.center.service;

import com.api.error.center.entity.SourceProfile;
import com.api.error.center.repository.SourceProfileRepository;
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
public class SourceProfileServiceTest {

    @MockBean
    SourceProfileRepository mockedSourceProfileRepository;

    @Autowired
    SourceProfileService sourceProfileService;

    @Before
    public void setUp() {

    }

    @Test
    public void testSave() {
        BDDMockito.given(mockedSourceProfileRepository.save(Mockito.any(SourceProfile.class))).willReturn(getMockedUserProfile());
        SourceProfile sourceProfile = sourceProfileService.save(new SourceProfile());
        assertNotNull(sourceProfile);;
        assertEquals(new Long(1), sourceProfile.getId());
        assertEquals("mockedUserProfile", sourceProfile.getAuthority());
    }

    @Test
    public void testFindById() {
        BDDMockito.given(mockedSourceProfileRepository.findById(Mockito.anyLong())).willReturn(Optional.of(getMockedUserProfile()));
        Optional<SourceProfile> userProfile = sourceProfileService.findById(1L);
        assertTrue(userProfile.isPresent());
        assertEquals(new Long(1), userProfile.get().getId());
        assertEquals("mockedUserProfile", userProfile.get().getAuthority());
    }

    @Test
    public void testFindByProfileName() {
        BDDMockito.given(mockedSourceProfileRepository.findByProfileName(Mockito.anyString())).willReturn(Optional.of(getMockedUserProfile()));
        Optional<SourceProfile> userProfile = sourceProfileService.findByProfileName("mockedUserProfile");
        assertTrue(userProfile.isPresent());
        assertEquals(new Long(1), userProfile.get().getId());
        assertEquals("mockedUserProfile", userProfile.get().getAuthority());
    }

    public SourceProfile getMockedUserProfile() {
        SourceProfile sourceProfile = new SourceProfile("mockedUserProfile");
        sourceProfile.setId(1L);
        return sourceProfile;
    }

}
