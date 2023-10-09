package com.lk.collaborative.blogging.service.impl;

import com.lk.collaborative.blogging.data.domain.Password;
import com.lk.collaborative.blogging.data.domain.Profile;
import com.lk.collaborative.blogging.data.domain.User;
import com.lk.collaborative.blogging.data.repository.ProfileRepository;
import com.lk.collaborative.blogging.data.repository.UserRepository;
import com.lk.collaborative.blogging.service.UserService;
import com.lk.collaborative.blogging.service.exception.AnonymousUserException;
import com.lk.collaborative.blogging.service.exception.UnauthorizedAccessException;
import com.lk.collaborative.blogging.service.exception.UserAlreadyExistsException;
import com.lk.collaborative.blogging.service.exception.UserNotFoundException;
import com.lk.collaborative.blogging.service.model.ProfileModel;
import com.lk.collaborative.blogging.service.model.UserSignUpModel;
import com.lk.collaborative.blogging.util.AuthenticationUtil;
import com.lk.collaborative.blogging.util.ExceptionMessageUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    public UserServiceImpl(UserRepository userRepository, ProfileRepository profileRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
    }

    @Override
    @Transactional
    public User signUp(UserSignUpModel userSignUpModel) {
        String userName = userSignUpModel.getUserName();
        // Check if user with this username is already present
        if(userRepository.findByUserNameIgnoreCase(userName).isPresent()) {
            throw new UserAlreadyExistsException(ExceptionMessageUtils.userExistsByUserName(userName));
        }

        String email = userSignUpModel.getEmail();
        // Check if user with this email is already present
        if(userRepository.findByEmailIgnoreCase(email).isPresent()) {
            throw new UserAlreadyExistsException(ExceptionMessageUtils.userExistsByEmail(email));
        }

        //Generate encrypted password
        Password password = Password.of(userSignUpModel.getPassword());

        //Save user
        User user = userRepository.save(new User(userName,
                userSignUpModel.getFirstName(),
                userSignUpModel.getLastName(),
                email,
                password));

        //Add a profile for the user
        Profile profile = new Profile(user);
        profileRepository.save(profile);
        return user;
    }

    @Override
    public Profile updateProfile(ProfileModel profileModel) {
        User user = null;
        try {
            user = AuthenticationUtil.getAuthenticatedUser();
        } catch (AnonymousUserException e) {
            throw new UnauthorizedAccessException(e);
        }

        //TODO: Check if user has permission to update profile

        Profile profile = user.getProfile();
        //Update profile
        profile.setCountryCode(profile.getCountryCode());
        profile.setGender(profileModel.getGender());
        profile.setPhoneNumber(profile.getPhoneNumber());
        profile.setDateOfBirth(profile.getDateOfBirth());
        return profileRepository.save(profile);
    }


}
