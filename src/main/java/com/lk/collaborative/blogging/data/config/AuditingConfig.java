package com.lk.collaborative.blogging.data.config;

import com.lk.collaborative.blogging.data.domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class AuditingConfig {

    @Bean
    public AuditorAware<User> auditorAware() {
        return new AuditAwareImpl();
    }
}
