/**
 * Copyright (c) 2016-2017 Toluwanimi Salako. http://csthings.net

 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package net.csthings.antreminder.services.account;

import java.io.Serializable;

import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;

import org.springframework.stereotype.Service;

import net.csthings.antreminder.entity.dto.AccountDto;
import net.csthings.common.db.exception.DatabaseException;
import net.csthings.common.dto.EmptyResultDto;
import net.csthings.common.dto.ResultDto;

@Service
public interface AccountService extends Serializable {

    public EmptyResultDto createAccount(@FormParam("email") String email, @FormParam("password") String password);

    public EmptyResultDto sendValidation(String email);

    public ResultDto<Boolean> validateAccount(@QueryParam("token") String verificationCode);

    public ResultDto<Boolean> changePassword(String email, String oldPassword, String newpassword);

    public ResultDto<Boolean> deleteAccount(String email, String password);

    public AccountDto getAccount(String email) throws DatabaseException;

    public boolean accountWithEmailExists(String email) throws DatabaseException;

}
