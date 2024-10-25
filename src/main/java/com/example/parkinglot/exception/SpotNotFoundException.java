package com.example.parkinglot.exception;

public class SpotNotFoundException extends RuntimeException {
    public SpotNotFoundException(String msg) {
        super(msg);
    }
}
