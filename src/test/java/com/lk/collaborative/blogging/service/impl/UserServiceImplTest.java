package com.lk.collaborative.blogging.service.impl;

import com.lk.collaborative.blogging.AbstractTest;
import com.lk.collaborative.blogging.data.domain.Password;
import com.lk.collaborative.blogging.data.domain.Profile;
import com.lk.collaborative.blogging.data.domain.User;
import com.lk.collaborative.blogging.data.repository.ProfileRepository;
import com.lk.collaborative.blogging.data.repository.UserRepository;
import com.lk.collaborative.blogging.service.UserService;
import com.lk.collaborative.blogging.service.exception.UserAlreadyExistsException;
import com.lk.collaborative.blogging.service.model.UserSignUpModel;
import com.lk.collaborative.blogging.util.ExceptionMessageUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.mockito.BDDMockito.*;


@SpringBootTest
class UserServiceImplTest extends AbstractTest {

    @MockBean
    UserRepository userRepository;

    @MockBean
    ProfileRepository profileRepository;

    @Autowired
    UserService userService;

    private String userName = "john.doe";
    private String firstName = "John";
    private String lastName = "Doe";
    private String email = "john.doe@email.com";
    private String password = "password";

    @Test
    public void signUp() {

        //Mock find by methods
        given(userRepository.findByUserNameIgnoreCase(userName))
                .willReturn(Optional.empty());
        given(userRepository.findByEmailIgnoreCase(email))
                .willReturn(Optional.empty());

        //Mock saving user in repository behaviour
        User user = new User(userName, firstName, lastName, email, Password.of(password));
        long userNameId = 1L;
        //Set id of the user using reflection as the setId method is protected
        ReflectionTestUtils.setField(user,"id", userNameId);
        given(userRepository.save(any()))
                .willReturn(user);

        //Mock saving profile in repository behaviour
        Profile profile = new Profile(user);
        long userProfileId = 2L;
        ReflectionTestUtils.setField(profile,"id", userProfileId);

        //Set profile for user
        ReflectionTestUtils.setField(user, "profile", profile);

        given(profileRepository.save(any()))
                .willReturn(profile);

        //save user
        User savedUser = userService.signUp(getUserSignUpModel());


        //user should get saved
        Assertions.assertEquals(savedUser, user);
        Assertions.assertEquals(savedUser.getId(), user.getId());

        //profile should get saved too
        Assertions.assertEquals(savedUser.getProfile(), profile);

    }

    @Test
    public void throwsExceptionIfUserAlreadyPresentWithUserName() {
        //Mock saving user in repository behaviour
        User user = new User(userName, firstName, lastName, email, Password.of(password));
        long userNameId = 1L;
        ReflectionTestUtils.setField(user,"id", userNameId);

        //Mock find by methods
        given(userRepository.findByUserNameIgnoreCase(userName))
                .willReturn(Optional.of(user));
        given(userRepository.findByEmailIgnoreCase(email))
                .willReturn(Optional.empty());

        Assertions.assertThrowsExactly(UserAlreadyExistsException.class,
                () -> userService.signUp(getUserSignUpModel()),
                ExceptionMessageUtils.userExistsByUserName(userName));

    }

    @Test
    public void throwsExceptionIfUserAlreadyPresentWithEmail() {
        //Mock saving user in repository behaviour
        User user = new User(userName, firstName, lastName, email, Password.of(password));
        long userNameId = 1L;
        ReflectionTestUtils.setField(user,"id", userNameId);

        //Mock find by methods
        given(userRepository.findByUserNameIgnoreCase(userName))
                .willReturn(Optional.empty());
        given(userRepository.findByEmailIgnoreCase(email))
                .willReturn(Optional.of(user));

        Assertions.assertThrowsExactly(UserAlreadyExistsException.class,
                () -> userService.signUp(getUserSignUpModel()),
                ExceptionMessageUtils.userExistsByEmail(email));

    }

    private UserSignUpModel getUserSignUpModel() {
        return new UserSignUpModel(userName, firstName, lastName, email, password);
    }



}