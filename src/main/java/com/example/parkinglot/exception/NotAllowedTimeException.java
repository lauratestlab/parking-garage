package com.example.parkinglot.exception;

public class NotAllowedTimeException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public NotAllowedTimeException() {
        super("Incorrect password");
    }
}
