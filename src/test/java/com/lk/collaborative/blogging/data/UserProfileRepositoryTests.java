package com.lk.collaborative.blogging.data;

import com.lk.collaborative.blogging.AbstractTest;
import com.lk.collaborative.blogging.data.domain.Gender;
import com.lk.collaborative.blogging.data.domain.Password;
import com.lk.collaborative.blogging.data.domain.Profile;
import com.lk.collaborative.blogging.data.domain.User;
import com.lk.collaborative.blogging.data.repository.ProfileRepository;
import com.lk.collaborative.blogging.data.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
@Slf4j
public class UserProfileRepositoryTests extends AbstractTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProfileRepository profileRepository;

    private static final String SAMPLE_USER_NAME = "John.doe";
    private static final String SAMPLE_USER_EMAIL = "joHn.doe@test.com";
    private  User johnDoe = null;


    @BeforeEach
    public void setUp() {
        johnDoe = new User(SAMPLE_USER_NAME, "John", "Doe",
                SAMPLE_USER_EMAIL, Password.of("test"));
    }

    @AfterEach
    public void remove() {
        profileRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void checksIfUserNameAndEmailAreSavedInLowerCase() {
        johnDoe = userRepository.save(johnDoe);
        Assertions.assertEquals(johnDoe.getEmail(), SAMPLE_USER_EMAIL.toLowerCase());
        Assertions.assertEquals(johnDoe.getUserName(), SAMPLE_USER_NAME.toLowerCase());
    }

    @Test
    public void findByUserName() {
        johnDoe = userRepository.save(johnDoe);
        Optional<User> user = userRepository.findByUserNameIgnoreCase(SAMPLE_USER_NAME);
        Assertions.assertTrue(user.isPresent());

        //Should return empty optional
        Optional<User> nonExistingUser = userRepository.findByUserNameIgnoreCase("gandalf.grey");
        Assertions.assertTrue(nonExistingUser.isEmpty());
    }

    @Test
    public void findByEmail() {
        johnDoe = userRepository.save(johnDoe);
        Optional<User> user = userRepository.findByEmailIgnoreCase(SAMPLE_USER_EMAIL);
        Assertions.assertTrue(user.isPresent());

        //Should return empty optional
        Optional<User> nonExistingUser = userRepository.findByEmailIgnoreCase("gandalf.grey@email.com");
        Assertions.assertTrue(nonExistingUser.isEmpty());
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
     * Test to validate if Profile is loaded upon calling {@link User#getProfile()} method.
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
