package com.lk.collaborative.blogging.service.model;

import com.lk.collaborative.blogging.data.domain.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * DTO for User Profile
 * Fields annotated for hibernate validations
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileModel {

    private LocalDateTime dateOfBirth;

    private Gender gender;

    @Size(min = 2, max = 2)
    private String countryCode;

    //TODO: develop @PhoneNumber validation to validate phoneNumber
    private String phoneNumber;
}
