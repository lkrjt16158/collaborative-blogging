package com.lk.collaborative.blogging.service.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionMessageUtilsTest {

    @Test
    public void userExceptions() {
        Assertions.assertEquals("User with username 'dummy' already exists", ExceptionMessageUtils.userExistsByUserName("dummy"));
    }
}