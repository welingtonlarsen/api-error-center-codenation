package com.api.error.center.repository;

import com.api.error.center.entity.Source;
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
public class SourceRepositoryTest {

    private Source source;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Before
    public void setUp() {
        this.source = new Source();
        source.setPassword("admin");
        source.setUsername("admin");
    }

    @Test
    @WithMockUser
    public void testSave() {
        Source response = userRepository.save(this.source);
        Assert.assertNotNull(response);
    }

    @Test
    @WithMockUser
    public void testFindByUsername() {
        Source sourceToFind = new Source();
        source.setPassword("userToFind");
        source.setUsername("userToFind");
        userRepository.save(sourceToFind);
        Optional<Source> response = userRepository.findByUsername(sourceToFind.getUsername());

        Assert.assertTrue(response.isPresent());
        Assert.assertEquals(response.get().getUsername(), sourceToFind.getUsername());
        Assert.assertEquals(response.get().getPassword(), sourceToFind.getPassword());
    }
}







