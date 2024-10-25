package com.example.parkinglot.exception;

import java.io.Serial;

public class NoSpotsAvailableException  extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public NoSpotsAvailableException() {
        super("No spots available");
    }
}