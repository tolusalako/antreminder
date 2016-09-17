package net.csthings.antreminder.entity;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

public class User {
    private String email;
    private Boolean authenticated;
    private String session;
    private UUID accountId;

    public User() {
    }

    public User(UUID accountId, String email, Boolean authenticated, String session) {
        this.accountId = accountId;
        this.email = email;
        this.authenticated = authenticated;
        this.session = session;
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

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public boolean isAuthenticated() {
        return authenticated && StringUtils.isNotEmpty(session) && StringUtils.isNotEmpty(email) && accountId != null;
    }
}
