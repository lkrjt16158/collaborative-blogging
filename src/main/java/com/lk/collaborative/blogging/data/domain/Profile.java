package com.lk.collaborative.blogging.data.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.time.LocalDateTime;

@Entity
@Table(name = "profile")
@Getter
@Setter
public class Profile extends AbstractPersistable<Long> {

    @OneToOne
    private User user;

    private LocalDateTime dateOfBirth;

    private Gender gender;

    @Column(length = 2)
    private String countryCode;

    private String phoneNumber;


}
