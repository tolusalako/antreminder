package net.csthings.antreminder.services.account;

import java.io.Serializable;

import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;

import org.springframework.stereotype.Service;

import net.csthings.common.dto.EmptyResultDto;
import net.csthings.common.dto.ResultDto;

@Service
public interface AccountService extends Serializable {

    public EmptyResultDto createAccount(@FormParam("email") String email, @FormParam("password") String password);

    public ResultDto<Boolean> validateAccount(@QueryParam("token") String verificationCode);

    public ResultDto<Boolean> changePassword(String email, String oldPassword, String newpassword);

    public ResultDto<Boolean> deleteAccount(String email, String password);

}
