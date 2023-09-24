package com.lk.collaborative.blogging.data;

import com.lk.collaborative.blogging.AbstractTest;
import com.lk.collaborative.blogging.data.domain.Gender;
import com.lk.collaborative.blogging.data.domain.Password;
import com.lk.collaborative.blogging.data.domain.Profile;
import com.lk.collaborative.blogging.data.domain.User;
import com.lk.collaborative.blogging.data.repository.ProfileRepository;
import com.lk.collaborative.blogging.data.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.parameters.P;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@Slf4j
public class UserProfileRepositoryTests extends AbstractTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProfileRepository profileRepository;

    private static final String SAMPLE_USER_NAME = "john.doe";
    private static final String SAMPLE_USER_EMAIL = "john.doe@test.com";
    private  User johnDoe = null;


    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        profileRepository.deleteAll();
        johnDoe = new User(SAMPLE_USER_NAME, "John", "Doe",
                SAMPLE_USER_EMAIL, Password.of("test"));
    }


    @Test
    public void findByUserName() {
        johnDoe = userRepository.save(johnDoe);
        Optional<User> user = userRepository.findByUserName(SAMPLE_USER_NAME);
        Assertions.assertTrue(user.isPresent());
        Assertions.assertEquals(user.get().getUserName(), SAMPLE_USER_NAME);
    }

    @Test
    public void findByEmail() {
        johnDoe = userRepository.save(johnDoe);
        Optional<User> user = userRepository.findByEmail(SAMPLE_USER_EMAIL);
        Assertions.assertTrue(user.isPresent());
        Assertions.assertEquals(user.get().getEmail(), SAMPLE_USER_EMAIL);
    }

    @Test
    public void findProfileByUser() {
        johnDoe = userRepository.save(johnDoe);

        Profile profile = new Profile(johnDoe);
        profile.setGender(Gender.MALE);
        profileRepository.save(profile);

        Optional<Profile> optionalProfile = profileRepository.findByUser(johnDoe);
        Assertions.assertTrue(optionalProfile.isPresent());
        profile = optionalProfile.get();
        Assertions.assertNotNull(profile.getId());

        User profileUser = profile.getUser();
        Assertions.assertNotNull(profileUser);
        Assertions.assertEquals(profileUser, johnDoe);
    }


    /**
     * Test to validate if Profile is loaded upon calling user.getProfile() method.
     * It will fail if annotated with {@Transactional} at method or class level.
     */
    @Test
    public void loadProfileFromUser() {
        johnDoe = userRepository.save(johnDoe);
        Assertions.assertNotNull(johnDoe);
        Long userID = johnDoe.getId();
        Assertions.assertNotNull(userID);

        Profile profile = new Profile(johnDoe);
        profile.setCountryCode("AU");
        profileRepository.save(profile);

        johnDoe = userRepository.findById(userID).get();
        profile = johnDoe.getProfile();
        Assertions.assertNotNull(profile);
        Assertions.assertNotNull(profile.getId());
        Assertions.assertEquals(profile.getCountryCode(), "AU");
    }
}
