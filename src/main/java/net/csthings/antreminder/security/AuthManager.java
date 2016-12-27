package net.csthings.antreminder.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import net.csthings.antreminder.entity.User;
import net.csthings.antreminder.services.account.LoginService;
import net.csthings.antreminder.utils.Status;
import net.csthings.common.dto.ResultDto;

@Component
public class AuthManager implements AuthenticationManager {

    @Autowired
    LoginService loginService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        ResultDto<User> loginResponse = loginService.login(authentication.getPrincipal().toString(),
                authentication.getCredentials().toString(), false);

        if (loginResponse == null || loginResponse.getStatus().equals(Status.FAILED))
            throw new BadCredentialsException(loginResponse == null ? "Wrong Credentials" : loginResponse.getMsg());

        // After successful login
        User user = loginResponse.getItem();
        Authentication auth = new AuthenticationImpl(user, user.getCookie());
        auth.setAuthenticated(true);
        return auth;
    }

}
