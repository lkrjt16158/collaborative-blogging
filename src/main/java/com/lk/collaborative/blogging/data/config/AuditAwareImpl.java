package com.lk.collaborative.blogging.data.config;

import com.lk.collaborative.blogging.data.domain.User;
import com.lk.collaborative.blogging.util.AuthenticationUtil;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditAwareImpl implements AuditorAware<User> {

    @Override
    public Optional<User> getCurrentAuditor() {
        return Optional.of(AuthenticationUtil.getAuthenticatedUser());
    }
}
