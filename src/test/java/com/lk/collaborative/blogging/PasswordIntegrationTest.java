package com.lk.collaborative.blogging;

import com.lk.collaborative.blogging.data.domain.Password;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

public class PasswordIntegrationTest {

    Password password = Password.of("password");

    @Test
    public void matchesIfPasswordIsCorrect() {
        Assertions.assertTrue(password.matches("password"));
        Assertions.assertFalse(password.matches("pass"));
    }


}
