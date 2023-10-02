package com.lk.collaborative.blogging.util;

import com.lk.collaborative.blogging.data.domain.User;
import com.lk.collaborative.blogging.service.exception.AnonymousUserException;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationUtil {
    private AuthenticationUtil() {}
    public static User getAuthenticatedUser() {
        Object authenticatedUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!(authenticatedUser instanceof User)){
            throw new AnonymousUserException(ExceptionMessageUtils.unauthenticatedUser());
        }
        return (User) authenticatedUser;
    }
}
