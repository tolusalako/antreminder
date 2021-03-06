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
package net.csthings.antreminder.services.account.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.Cookie;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import net.csthings.antreminder.entity.User;
import net.csthings.antreminder.entity.dto.AccountDto;
import net.csthings.antreminder.entity.dto.AccountSessionDto;
import net.csthings.antreminder.entity.dto.EmailAccountDto;
import net.csthings.antreminder.entity.dto.SessionAccountDto;
import net.csthings.antreminder.repo.AccountDao;
import net.csthings.antreminder.repo.AccountSessionDao;
import net.csthings.antreminder.repo.EmailAccountDao;
import net.csthings.antreminder.repo.SessionAccountDao;
import net.csthings.antreminder.security.LogoutManager;
import net.csthings.antreminder.services.account.LoginService;
import net.csthings.antreminder.services.account.utils.AccountError;
import net.csthings.antreminder.services.account.utils.AccountStatus;
import net.csthings.common.context.CommonThreadContext;
import net.csthings.common.dto.ResultDto;
import net.csthings.common.utils.CommonError;
import net.csthings.common.utils.CommonUtils;
import net.csthings.common.utils.Status;
import net.csthings.security.utils.PasswordHash;
import net.csthings.security.utils.PasswordHash.CannotPerformOperationException;
import net.csthings.security.utils.PasswordHash.InvalidHashException;
import net.csthings.security.utils.SecurityUtils;

public final class LoginServiceImpl implements LoginService {
    private static Logger LOG = LoggerFactory.getLogger(AccountServiceImpl.class);
    public static final int SESSION_TOKEN_LENGTH = 128;
    private static final int SESSION_LIFE = 24; // HRS
    @Autowired
    EmailAccountDao emailAccountDao;

    @Autowired
    AccountDao accountDao;

    @Autowired
    SessionAccountDao sessionAccountDao;

    @Autowired
    AccountSessionDao accountSessionDao;

    public LoginServiceImpl() {
    }

    @Override
    public ResultDto<User> login(String email, String password, boolean rememberMe) {
        if (StringUtils.isAnyEmpty(email, password))
            return new ResultDto<>(null, Status.FAILED, CommonError.INVALID_PARAMETERS, "Email or password is invalid");

        EmailAccountDto emailAccount = null;
        try {
            emailAccount = emailAccountDao.findOne(email);

            if (emailAccount == null)
                return new ResultDto<>(null, Status.FAILED, CommonError.DNE, "Account does not exist.");

            AccountDto account = accountDao.findOne(emailAccount.getAccountId());

            // Users can't login until they have validated their accounts
            if (account.getStatus() == AccountStatus.NEW) {
                String resendValidationLink = StringUtils.join("/validate?email=", email);
                return new ResultDto<>(null, Status.FAILED, AccountError.NONVALIDATED,
                        String.format(
                                "Your account hasn't been validated. Please check your email or click %s to resend validation code.",
                                String.format("<a href=\"%s\">here</a>", resendValidationLink)));
            }

            boolean passwordMatch = PasswordHash.verifyPassword(password, account.getPassword());

            if (!passwordMatch)
                return new ResultDto<>(null, Status.FAILED, CommonError.GENERAL_ERROR, "Wrong password.");

            AccountSessionDto session = createSession(account.getAccountId(), rememberMe);

            if (CommonUtils.isNull(session))
                return new ResultDto<>(null, Status.FAILED, CommonError.UNEXPECTED_ERROR,
                        "Could not create login session");
            else {
                return new ResultDto<>(getUser(session, email), Status.SUCCESS);
            }

        }
        catch (CannotPerformOperationException | HibernateException | InvalidHashException e) {
            LOG.error("Failed to login {}", email, e);
            return new ResultDto<>(null, Status.FAILED, CommonError.UNEXPECTED_ERROR, e.getMessage());
        }

    }

    @Override
    public ResultDto<Boolean> validateSession(String sessionId) {
        String ip = (String) CommonThreadContext.get(CommonThreadContext.IP, "");
        try {
            AccountSessionDto session = getExistingSessionAccount(sessionId);
            if (session == null || session.getExpiration().getTime() < new Date().getTime())
                return new ResultDto<>(false, Status.FAILED, CommonError.TOKEN_EXPIRED);
            else if (!ip.equalsIgnoreCase(session.getIp()))
                return new ResultDto<>(false, Status.FAILED, "IP_CHANGED");
            AccountDto account = accountDao.findOne(session.getAccountId());
            if (account != null) {
                getUser(session, account.getEmail());
                return new ResultDto<>(true);
            }
        }
        catch (HibernateException e) {
            LOG.error("Failed to validate session {}", sessionId, e);
        }
        return new ResultDto<>(false, Status.FAILED);
    }

    private AccountSessionDto getExistingSession(UUID accountId) {
        return accountSessionDao.findOne(accountId);
    }

    private AccountSessionDto getExistingSessionAccount(String sessionId) {
        SessionAccountDto dto = sessionAccountDao.findOne(sessionId);
        if (dto != null)
            return accountSessionDao.findOne(dto.getAccountId());
        return null;
    }

    private AccountSessionDto createSession(UUID accountId, boolean rememberMe) {
        AccountSessionDto session = getExistingSession(accountId);

        try {
            if (session != null)
                accountSessionDao.delete(accountId);

            Calendar cal = Calendar.getInstance();
            String ip = (String) CommonThreadContext.get(CommonThreadContext.IP, "");
            cal.setTime(new Date());
            session = new AccountSessionDto();
            cal.add(Calendar.HOUR, rememberMe ? SESSION_LIFE * 14 : SESSION_LIFE); // 2Weeks
            session.setExpiration(cal.getTime());
            session.setAccountId(accountId);
            session.setIp(ip);
            // TODO use encoding / decoding with accountid
            String sessionId = PasswordHash.toBase64(SecurityUtils.generateRandomBytes(SESSION_TOKEN_LENGTH));
            session.setSessionId(sessionId);

            SessionAccountDto sessionAccount = new SessionAccountDto();
            sessionAccount.setAccountId(accountId);
            sessionAccount.setSessionId(session.getSessionId());

            sessionAccountDao.save(sessionAccount);
            accountSessionDao.save(session);
            return session;
        }
        catch (HibernateException e) {
            LOG.error("Could not save session", e);
            return null;
        }
    }

    private static User getUser(AccountSessionDto session, String email) {
        Cookie cookie = new Cookie(LogoutManager.SESSION_NAME, session.getSessionId());
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(
                (int) TimeUnit.MILLISECONDS.toSeconds(session.getExpiration().getTime() - new Date().getTime()));
        User user = new User(session.getAccountId(), email, true, cookie);
        // Authentication authentication = new AuthenticationImpl(user,
        // session.getSessionId());
        // SecurityContextHolder.getContext().setAuthentication(authentication);
        return user;
    }

    @Override
    public void logout(UUID accountId) {
        AccountSessionDto ac = getExistingSession(accountId);
        if (ac != null)
            accountSessionDao.delete(ac);
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
