package net.csthings.antreminder.services.account.impl;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.hibernate.exception.GenericJDBCException;
import org.hibernate.validator.constraints.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import net.csthings.antreminder.config.AppSettings;
import net.csthings.antreminder.entity.dto.AccountDto;
import net.csthings.antreminder.entity.dto.EmailAccountDto;
import net.csthings.antreminder.entity.dto.ValidationAccountDto;
import net.csthings.antreminder.repo.AccountDao;
import net.csthings.antreminder.repo.AccountSessionDao;
import net.csthings.antreminder.repo.EmailAccountDao;
import net.csthings.antreminder.repo.SessionAccountDao;
import net.csthings.antreminder.repo.ValidationAccountDao;
import net.csthings.antreminder.services.account.AccountService;
import net.csthings.antreminder.services.account.exception.AccountException;
import net.csthings.antreminder.services.account.utils.AccountError;
import net.csthings.antreminder.services.account.utils.AccountStatus;
import net.csthings.antreminder.services.account.utils.ValidationUtils;
import net.csthings.antreminder.services.email.EmailService;
import net.csthings.antreminder.services.email.exception.EmailException;
import net.csthings.common.db.exception.DatabaseException;
import net.csthings.common.dto.EmptyResultDto;
import net.csthings.common.dto.ResultDto;
import net.csthings.common.utils.CommonError;
import net.csthings.common.utils.Status;
import net.csthings.security.utils.PasswordHash;
import net.csthings.security.utils.PasswordHash.CannotPerformOperationException;
import net.csthings.security.utils.SecurityUtils;

public final class AccountServiceImpl implements AccountService {

    private static Logger LOG = LoggerFactory.getLogger(AccountServiceImpl.class);

    private static final int VALIDATION_TOKEN_LENGTH = 35;

    private String apiUrl = "localhost:8080";
    private static final String SIGN_OFF = "Antreminder Support Team";

    ExecutorService executors;

    @Autowired
    AccountDao accountDao;
    @Autowired
    AccountSessionDao accountSessionDao;
    @Autowired
    EmailAccountDao emailAccountDao;
    @Autowired
    SessionAccountDao sessionAccountDao;
    @Autowired
    ValidationAccountDao validationAccountDao;
    @Autowired
    EmailService emailService;
    @Autowired
    AppSettings appSettings;

    public AccountServiceImpl() {
        executors = Executors.newFixedThreadPool(10);
    }

    @PostConstruct
    public void init() {
        apiUrl = appSettings.getHost();
    }

    @Override
    public EmptyResultDto createAccount(@Email String email, String password) {
        if (StringUtils.isAnyEmpty(email, password) || !EmailValidator.getInstance().isValid(email)) {
            LOG.error("{} {} {}", Status.FAILED, CommonError.INVALID_PARAMETERS, "Email or password is invalid");
            return new EmptyResultDto(Status.FAILED, CommonError.INVALID_PARAMETERS, "Email or password is invalid");
        }

        try {
            if (accountWithEmailExists(email)) {
                return new EmptyResultDto(Status.FAILED, CommonError.INVALID_PARAMETERS, "Account already exists");
            }

            ValidationUtils.validatePassword(email, password);

            UUID accountid = UUID.randomUUID();

            String passHash = PasswordHash.createHash(password);
            AccountDto newAccount = AccountDto.createNewAccount(email, passHash);
            newAccount.setAccountId(accountid);
            EmailAccountDto emailAccount = new EmailAccountDto(email, accountid);

            accountDao.save(newAccount);
            emailAccountDao.save(emailAccount);

            // Send validation email
            sendValidation(email, accountid);

            return new EmptyResultDto(Status.SUCCESS, StringUtils.EMPTY,
                    "Account successfuly registered. Please check your email for your verification link.");
        }
        catch (DatabaseException | GenericJDBCException | CannotPerformOperationException e) {
            LOG.error("Failed to register {}", email, e);
            return new EmptyResultDto(Status.FAILED, CommonError.UNEXPECTED_ERROR, e.getMessage());
        }
        catch (AccountException e) {
            LOG.error("Failed to register {}", email, e);
            return new EmptyResultDto(Status.FAILED, CommonError.INVALID_PARAMETERS, e.getMessage());
        }
    }

    @Override
    public EmptyResultDto sendValidation(String email) {
        AccountDto account = null;
        try {
            account = getAccount(email);
        }
        catch (DatabaseException e) {
            LOG.error("Could not find account for email {}", email, e);
        }
        if (null == account)
            return new EmptyResultDto(Status.FAILED, CommonError.DNE, "Could not find an account for this email.");

        return sendValidation(email, account.getAccountId());
    }

    EmptyResultDto sendValidation(String email, UUID accountId) {
        String token = SecurityUtils.generateToken(VALIDATION_TOKEN_LENGTH);
        ValidationAccountDto emailValidation = new ValidationAccountDto(accountId, token);
        sendValidationEmail(email, token);
        validationAccountDao.save(emailValidation);

        return new EmptyResultDto(Status.SUCCESS, StringUtils.EMPTY, "Check your email for a verification link");
    }

    @Override
    public ResultDto<Boolean> validateAccount(String token) {
        if (StringUtils.isEmpty(token))
            return new ResultDto<>(false, Status.FAILED, CommonError.INVALID_PARAMETERS, "Validation token is invalid");

        try {
            ValidationAccountDto validationDto = validationAccountDao.findOne(token);

            if (validationDto != null) {
                AccountDto account = getAccount(validationDto.getAccountId());
                if (validationDto.getToken().equals(token) && account != null) {
                    account.setStatus(AccountStatus.ACTIVE);
                    accountDao.save(account);
                    validationAccountDao.save(validationDto);
                    return new ResultDto<>(true, Status.SUCCESS, "Account validated successfully. Please login.", "");
                }
                else {
                    return new ResultDto<>(false, Status.FAILED, CommonError.DNE, "Account does not exist");
                }

            }
            return new ResultDto<>(false, Status.FAILED, AccountError.TOKEN_EXPIRED, "Validation Token Expired");
        }
        catch (Exception e) {
            LOG.error("Failed to validate account: {}", token, e);
            return new ResultDto<>(false, Status.FAILED, CommonError.UNEXPECTED_ERROR, e.getMessage());
        }
    }

    @Override
    public ResultDto<Boolean> changePassword(String email, String oldPassword, String newpassword) {
        return null;
    }

    @Override
    public ResultDto<Boolean> deleteAccount(String email, String password) {
        // TODO Auto-generated method stub
        return null;
    }

    private void sendValidationEmail(String email, String token) {
        executors.submit(() -> {
            String validationLink = StringUtils.join(apiUrl, "/validate?token=", token);
            try {
                emailService.sendHtmlEmail(ValidationUtils.EMAIL_VALIDATION_TITLE,
                        ValidationUtils.createValidationEmail(email, validationLink, SIGN_OFF), new String[] { email },
                        null, null);
            }
            catch (EmailException e) {
                LOG.error("Could not send validation email to {}", email, e);
            }
        });
    }

    private AccountDto getAccount(String email) throws DatabaseException {
        EmailAccountDto rez = emailAccountDao.findOne(email);
        if (null == rez)
            return null;

        return getAccount(rez.getAccountId());
    }

    private AccountDto getAccount(UUID accountId) throws DatabaseException {
        return accountDao.findOne(accountId);
    }

    private boolean accountWithEmailExists(String email) throws DatabaseException {
        return getAccount(email) != null;
    }

}
