package com.lk.collaborative.blogging;

import com.lk.collaborative.blogging.data.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collection;

@ActiveProfiles("test")
public abstract class AbstractTest {


    /**
     * Method to set Security Context for testing purposes.
     * @param user User to be set as current authenticated user
     */
    protected void setUpSecurityContext ( User user) {
        SecurityContextHolder.getContext()
                .setAuthentication(new Authentication() {
                    @Override
                    public Collection<? extends GrantedAuthority> getAuthorities() {
                        return null;
                    }

                    @Override
                    public Object getCredentials() {
                        return null;
                    }

                    @Override
                    public Object getDetails() {
                        return null;
                    }

                    @Override
                    public Object getPrincipal() {
                        return user;
                    }

                    @Override
                    public boolean isAuthenticated() {
                        return true;
                    }

                    @Override
                    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

                    }

                    @Override
                    public String getName() {
                        return user.getUserName();
                    }
                });
    }
}
