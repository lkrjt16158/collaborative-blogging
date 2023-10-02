package com.lk.collaborative.blogging.service.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * DTO class for user to sign up
 * Fields annotated for hibernate validation
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSignUpModel {

    @NotBlank
    private String userName;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    //TODO: Develop @Password annotation to ensure strength of the password.
    private String password;

}
