package net.csthings.antreminder.security;

import java.util.Collection;

import javax.servlet.http.Cookie;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import net.csthings.antreminder.entity.User;

@Component
public class AuthenticationImpl extends AbstractAuthenticationToken implements Authentication {
    private User user;
    private boolean authenticated;

    public AuthenticationImpl() {
        super(null);
    }

    public AuthenticationImpl(User user, Cookie cookie) {
        this();
        this.user = user;
        authenticated = user.getAuthenticated();
    }

    @Override
    public String getName() {
        return user.getEmail();
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return null;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String getCredentials() {
        return null;
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
