package net.csthings.antreminder.security;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import net.csthings.antreminder.entity.User;

public class SecurityUtils {
    public static final String ANONYMOUS_USER = "anonymousUser";

    public static boolean isAuthenticated() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.isAuthenticated() && !auth.getName().equals(ANONYMOUS_USER)
                && auth.getCredentials() instanceof String && StringUtils.isNotEmpty((String) auth.getCredentials())
                && auth.getPrincipal() != null && auth.getPrincipal() instanceof User
                && ((User) auth.getPrincipal()).isAuthenticated();
    }

    public static UUID getAccountId() {
        return ((AuthenticationImpl) SecurityContextHolder.getContext().getAuthentication()).getPrincipal()
                .getAccountId();
    }

    public static String getEmail() {
        return ((AuthenticationImpl) SecurityContextHolder.getContext().getAuthentication()).getPrincipal().getEmail();
    }
}
