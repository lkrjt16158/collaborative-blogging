package com.lk.collaborative.blogging.data.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.time.LocalDateTime;

@Entity
@Table(name = "profile")
@Getter
@Setter
@NoArgsConstructor
public class Profile extends AbstractPersistable<Long> {

    public Profile(User user) {
        this.user = user;
    }

    @OneToOne(optional = false)
    private User user;

    private LocalDateTime dateOfBirth;

    private Gender gender;

    @Column(length = 2)
    private String countryCode;

    private String phoneNumber;


}
