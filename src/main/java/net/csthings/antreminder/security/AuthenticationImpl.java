package net.csthings.antreminder.security;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import net.csthings.antreminder.entity.User;

public class AuthenticationImpl implements Authentication {
    public static final String AUTH_NAME = "ANTREMINDER_AUTH";

    private String session;
    private User user;
    private boolean authenticated;

    public AuthenticationImpl(User user, String session) {
        this.session = session;
        this.user = user;
        authenticated = user.getSession().equals(session);
    }

    @Override
    public String getName() {
        return AUTH_NAME;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getCredentials() {
        return session;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public User getPrincipal() {
        return user;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

}
