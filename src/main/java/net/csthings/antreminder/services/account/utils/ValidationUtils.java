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

    public static final String PLACEHOLDER_VERIFY = "${verifyAccount}";
    public static final String PLACEHOLDER_USERNAME = "${username}";
    public static final String PLACEHOLDER_SIGNOFF = "${signoff}";
    public static final String PLACEHOLDER_REMINDERS = "${viewReminders}";
    public static final String PLACEHOLDER_MESSAGE = "${message}";

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

    public static String createValidationEmail(String email, String link, String signoff) {
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
