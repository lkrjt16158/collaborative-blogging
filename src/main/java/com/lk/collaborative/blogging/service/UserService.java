package com.lk.collaborative.blogging.service;

import com.lk.collaborative.blogging.data.domain.User;
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

}
