package com.lk.collaborative.blogging.data.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class User extends AbstractPersistable<Long> {

    public User(String userName, String firstName, String lastName, String email, Password password) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    @Column (nullable = false, unique = true)
    private String userName;

    @Column(nullable = false)
    private String firstName;

    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @Convert(converter = Password.PasswordConverter.class)
    @JsonIgnore
    private Password password;

    @OneToOne(mappedBy = "user")
    @Setter(value = AccessLevel.PRIVATE)
    private Profile profile;


}
