package com.example.parkinglot.exception;

public class PaymentMethodNotFoundException extends RuntimeException {
    public PaymentMethodNotFoundException(String msg) {
        super(msg);
    }
}
