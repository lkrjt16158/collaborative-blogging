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

    public static String articleNotFound(String url) {
        return String.format("No article exists with identifier: %s. ", url);
    }

    public static String unauthorizedArticleAccess(String url) {
        return String.format("User is not authorized to perform action on the article: %s .", url);
    }

}
