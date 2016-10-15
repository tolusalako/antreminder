package net.csthings.antreminder.services.account.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import net.csthings.antreminder.services.account.exception.AccountException;

public final class ValidationUtils {
    public static final String EMAIL_VALIDATION_TITLE = "Antreminder Account Verification";
    public static final String EMAIL_VALIDATION_TEXT;

    private static final String PLACEHOLDER_VERIFY = "${verifyAccount}";
    private static final String PLACEHOLDER_USERNAME = "${username}";
    private static final String PLACEHOLDER_SIGNOFF = "${signoff}";

    private static final int MIN_PASSWORD_LENGTH = 8;

    static {
        try {
            EMAIL_VALIDATION_TEXT = FileUtils.readFileToString(new File("resources/email/email_validate.html"),
                    Charset.forName("UTF-8"));
        }
        catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static String getValidationEmail(String email, String link, String signoff) {
        String text = StringUtils.replace(EMAIL_VALIDATION_TEXT, PLACEHOLDER_USERNAME, email);
        text = StringUtils.replace(text, PLACEHOLDER_SIGNOFF, signoff);
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
