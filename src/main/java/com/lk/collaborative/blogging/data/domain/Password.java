package com.lk.collaborative.blogging.data.domain;

import jakarta.persistence.AttributeConverter;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Class representing user password.
 * It can be initialized only by calling static {@link Password#of(String)} method.
 * Any access to encrypted password is strictly prohibited.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Password {

    private Password(String password) {
        this.encryptedPassword = password;
    }

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private String encryptedPassword;

    public static Password of(@NonNull String rawPassword) {
        return new Password(encryptPassword(rawPassword));
    }


    private static String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public boolean matches(@NonNull String password) {
        return passwordEncoder.matches(password, encryptedPassword);
    }


    @Override
    public String toString() {
        return "Password{" +
                "password= ********** }";
    }

    static class PasswordConverter implements AttributeConverter<Password, String> {

        @Override
        public String convertToDatabaseColumn(Password password) {
            return password.encryptedPassword;
        }

        @Override
        public Password convertToEntityAttribute(String s) {
            return new Password(s);
        }
    }
}
