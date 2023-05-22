package com.task.exception;

public class StoreUsersNotFoundException extends RuntimeException{
    public StoreUsersNotFoundException() {
    }

    public StoreUsersNotFoundException(String message) {
        super(message);
    }

    public StoreUsersNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public StoreUsersNotFoundException(Throwable cause) {
        super(cause);
    }

    public StoreUsersNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
