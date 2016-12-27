package net.csthings.antreminder.services.account;

import java.util.UUID;

import javax.ws.rs.FormParam;

import org.springframework.stereotype.Service;

import net.csthings.antreminder.entity.User;
import net.csthings.common.dto.ResultDto;

@Service
public interface LoginService {
    public ResultDto<User> login(@FormParam("email") String email, @FormParam("password") String password,
            boolean rememberMe);

    public void logout(UUID accountId);

    public ResultDto<Boolean> validateSession(String sessionId);
    // public String extendSession(String sessionId, UUID accountId, String ip);

}
