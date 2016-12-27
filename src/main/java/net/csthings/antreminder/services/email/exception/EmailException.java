package net.csthings.antreminder.services.email.exception;

public class EmailException extends Exception {
    private static final long serialVersionUID = -3610531222020693705L;

    public EmailException(String message) {
        super(message);
    }

    public EmailException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
