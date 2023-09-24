package com.lk.collaborative.blogging.data;

import com.lk.collaborative.blogging.data.domain.Password;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PasswordIntegrationTest {

    Password password = Password.of("password");

    @Test
    public void matchesIfPasswordIsCorrect() {
        Assertions.assertTrue(password.matches("password"));
        Assertions.assertFalse(password.matches("pass"));
    }


}
