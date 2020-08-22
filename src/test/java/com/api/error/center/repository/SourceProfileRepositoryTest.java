package com.api.error.center.repository;

import com.api.error.center.entity.SourceProfile;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SourceProfileRepositoryTest {

    @Autowired
    private SourceProfileRepository sourceProfileRepository;

    @BeforeEach
    public void setUp() {
        SourceProfile sourceProfile = new SourceProfile("SourceProfile");
        SourceProfile response = sourceProfileRepository.save(sourceProfile);
    }

    @Test
    public void testSave() {
        SourceProfile sourceProfile = new SourceProfile("TestSaveProfileName");
        SourceProfile response = sourceProfileRepository.save(sourceProfile);

        assertNotNull(response);
        assertEquals(2L, response.getId());
        assertEquals("TestSaveProfileName", response.getAuthority());
    }

    @Test
    public void testFindById() {
        Optional<SourceProfile> sourceProfile = sourceProfileRepository.findById(1L);

        Assert.assertNotNull(sourceProfile);
        Assert.assertEquals(new Long(1), sourceProfile.get().getId());
        Assert.assertEquals("SourceProfile", sourceProfile.get().getAuthority());
    }

    @Test
    public void testFindByProfileName() {
        Optional<SourceProfile> userProfile = sourceProfileRepository.findByProfileName("SourceProfile");

        Assert.assertNotNull(userProfile);
        Assert.assertEquals(new Long(1), userProfile.get().getId());
        Assert.assertEquals("SourceProfile", userProfile.get().getAuthority());
    }
}
