package net.csthings.antreminder.services.account.utils;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import net.csthings.antreminder.services.account.exception.AccountException;
import net.csthings.common.configuration.ConfigurationService;

public final class ValidationUtils {

    private static final String EMAIL_KEY = "account.email";
    private static final String TITLE_KEY = "account.validationemail.title";
    private static final String FILE_KEY = "account.validationemail.file";

    public String emailValidationTitle;
    public String emailValidationText;

    private final String PLACEHOLDER_VERIFY = "${verifyAccount}";
    private final String PLACEHOLDER_USERNAME = "${username}";

    private static final int MIN_PASSWORD_LENGTH = 8;

    public ValidationUtils(ConfigurationService configService) throws IOException {
        // String validationEmailFile = Paths
        // .get(System.getProperty("user.dir").toString(),
        // configService.getString(FILE_KEY)).toString();
        // emailValidationText = FileUtils.readFileToString(new
        // File(validationEmailFile), Charset.forName("UTF-8"));
    }

    public String getValidationEmail(String email, String link) {
        String text = StringUtils.replace(emailValidationText, PLACEHOLDER_USERNAME, email);
        return StringUtils.replace(text, PLACEHOLDER_VERIFY, link);
    }

    public static boolean validatePassword(String email, String password) throws AccountException {
        if (email.contains(password))
            throw new AccountException("Password must not be part of your email.");

        if (password.length() < MIN_PASSWORD_LENGTH)
            throw new AccountException(String.format("Password must be at least %s characters.", MIN_PASSWORD_LENGTH));

        return true;
    }

}
