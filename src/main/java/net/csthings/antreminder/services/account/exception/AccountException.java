package net.csthings.antreminder.services.account.exception;

import net.csthings.common.exceptions.CsthingsException;

public class AccountException extends CsthingsException {

    private static final long serialVersionUID = -4824080002343988735L;

    public AccountException(String message) {
        super(message);
    }

    public AccountException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
