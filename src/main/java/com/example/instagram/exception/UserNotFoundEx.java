package com.example.instagram.exception;

public class UserNotFoundEx extends RuntimeException{

    public UserNotFoundEx() {
        super();
    }

    public UserNotFoundEx(String message) {
        super(message);
    }

    public UserNotFoundEx(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotFoundEx(Throwable cause) {
        super(cause);
    }

    protected UserNotFoundEx(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
