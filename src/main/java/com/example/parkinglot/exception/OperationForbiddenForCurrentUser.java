package com.example.parkinglot.exception;

public class OperationForbiddenForCurrentUser extends RuntimeException {
    public OperationForbiddenForCurrentUser() {
        super("Operation forbidden for current user");
    }
}
