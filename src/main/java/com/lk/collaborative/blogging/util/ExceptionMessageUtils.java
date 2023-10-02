package com.lk.collaborative.blogging.util;

public class ExceptionMessageUtils {
    private ExceptionMessageUtils(){}
    public static String userExistsByUserName(String userName) {
        return String.format("User with username '%s' already exists", userName);
    }

    public static String userExistsByEmail(String email) {
        return String.format("User with email '%s' already exists", email);
    }

    public static String unauthenticatedUser() {
        return "User is not authenticated";
    }

}
