package net.csthings.antreminder.entity;

import java.util.UUID;

import javax.servlet.http.Cookie;

import org.apache.commons.lang3.StringUtils;

public class User {
    private String email;
    private Boolean authenticated;
    private Cookie cookie;
    private UUID accountId;

    public User() {
    }

    public User(UUID accountId, String email, Boolean authenticated, Cookie cookie) {
        this.accountId = accountId;
        this.email = email;
        this.authenticated = authenticated;
        this.cookie = cookie;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(Boolean authenticated) {
        this.authenticated = authenticated;
    }

    public Cookie getCookie() {
        return cookie;
    }

    public void setCookie(Cookie cookie) {
        this.cookie = cookie;
    }

    public boolean isAuthenticated() {
        return authenticated && null != cookie && StringUtils.isNotEmpty(email) && accountId != null;
    }
}
