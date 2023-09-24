package com.lk.collaborative.blogging.data.config;

import com.lk.collaborative.blogging.data.domain.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditAwareImpl implements AuditorAware<User> {

    @Override
    public Optional<User> getCurrentAuditor() {
        return Optional.of((User)SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal());
    }
}
