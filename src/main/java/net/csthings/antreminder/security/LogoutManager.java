package net.csthings.antreminder.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import net.csthings.antreminder.entity.User;

@Service
public class LogoutManager implements LogoutHandler {
    public static final String SESSION_NAME = "eatercookie";

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        User user = ((AuthenticationImpl) authentication).getPrincipal();
        // Delete session from db here (if required)
        user.setAuthenticated(false);
        user.getCookie().setMaxAge(0);
    }

}
