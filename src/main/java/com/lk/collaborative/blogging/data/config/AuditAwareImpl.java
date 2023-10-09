package com.lk.collaborative.blogging.data.config;

import com.lk.collaborative.blogging.data.domain.User;
import com.lk.collaborative.blogging.service.exception.AnonymousUserException;
import com.lk.collaborative.blogging.service.exception.UnauthorizedAccessException;
import com.lk.collaborative.blogging.util.AuthenticationUtil;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditAwareImpl implements AuditorAware<User> {

    @Override
    public Optional<User> getCurrentAuditor() {
        try {
            return Optional.of(AuthenticationUtil.getAuthenticatedUser());
        } catch (AnonymousUserException e) {
            throw new UnauthorizedAccessException(e);
        }
    }
}
