package com.lk.collaborative.blogging.service.util;

import com.lk.collaborative.blogging.util.ExceptionMessageUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ExceptionMessageUtilsTest {

    @Test
    public void userExceptions() {
        Assertions.assertEquals("User with username 'dummy' already exists", ExceptionMessageUtils.userExistsByUserName("dummy"));
    }
}