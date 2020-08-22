package com.api.error.center.repository;

import com.api.error.center.entity.Source;
import com.api.error.center.entity.SourceProfile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SourceRepositoryTest {

    @Autowired
    private SourceProfileRepository sourceProfileRepository;

    @Autowired
    private SourceRepository sourceRepository;

    @Test
    @WithMockUser
    public void testSave() {
        SourceProfile sourceProfile = new SourceProfile("TestSaveSourceProfile");
        sourceProfile = sourceProfileRepository.save(sourceProfile);
        Source source = new Source("TestSaveUsername", "TestSavePassword", sourceProfile);

        Source response = sourceRepository.save(source);

        assertEquals(1L, response.getId());
        assertEquals("TestSaveUsername", response.getUsername());
        assertEquals("TestSavePassword", response.getPassword());
        assertEquals("TestSaveSourceProfile", response.getSourceProfile().getAuthority());
    }

    @Test
    @WithMockUser
    public void testFindByUsername() {
        SourceProfile sourceProfile = new SourceProfile("TestFindByUsernameSourceProfile");
        sourceProfile = sourceProfileRepository.save(sourceProfile);
        Source source = new Source("TestFindByUsernameUsername", "TestFindByUsernamePassword", sourceProfile);
        sourceRepository.save(source);

        Optional<Source> response = sourceRepository.findByUsername("TestFindByUsernameUsername");

        assertEquals(1L, response.get().getId());
        assertEquals("TestFindByUsernameUsername", response.get().getUsername());
        assertEquals("TestFindByUsernamePassword", response.get().getPassword());
        assertEquals("TestFindByUsernameSourceProfile", response.get().getSourceProfile().getAuthority());
    }
}







