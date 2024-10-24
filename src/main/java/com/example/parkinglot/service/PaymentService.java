package com.example.parkinglot.service;

import com.example.parkinglot.entity.PaymentMethod;
import com.example.parkinglot.exception.PaymentMethodNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.SimpleErrors;
import org.springframework.validation.Validator;

@Service
public class PaymentService {

    private final Validator validator;

    public PaymentService(Validator validator) {
        this.validator = validator;
    }

    public void processPayment(PaymentMethod paymentMethod) {
        Errors errors = new SimpleErrors(paymentMethod);
        errors.failOnError(PaymentMethodNotFoundException::new);
        validator.validate(paymentMethod, errors);
        errors.hasErrors();
    }

}
