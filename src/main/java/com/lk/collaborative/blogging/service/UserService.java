package com.lk.collaborative.blogging.service;

import com.lk.collaborative.blogging.data.domain.Profile;
import com.lk.collaborative.blogging.data.domain.User;
import com.lk.collaborative.blogging.service.model.ProfileModel;
import com.lk.collaborative.blogging.service.model.UserSignUpModel;

import java.util.Optional;

/**
 * User Service Contract
 */
public interface UserService {
    /**
     * Add user to the database
     *
     * @param userSignUpModel model with new user details
     * @return newly added user
     */
     User signUp(UserSignUpModel userSignUpModel);

    /**
     * Update user Profile
     *
     * @param profileModel model with profile fields
     * @return updated profile
     */
     Profile updateProfile(ProfileModel profileModel);

}
