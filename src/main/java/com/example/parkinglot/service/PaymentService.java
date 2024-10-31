package com.example.parkinglot.service;

import com.example.parkinglot.dto.ReservationDTO;
import com.example.parkinglot.entity.PaymentMethod;
import com.example.parkinglot.entity.User;
import com.example.parkinglot.exception.PaymentMethodNotFoundException;
import com.example.parkinglot.mapper.PaymentMethodMapper;
import com.example.parkinglot.mapper.PaymentMethodMapperImpl;
import com.example.parkinglot.repo.PaymentMethodRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.SimpleErrors;
import org.springframework.validation.Validator;

import java.util.Objects;

@Service
public class PaymentService {

    private final Validator validator;
    private final PaymentMethodMapper paymentMethodMapper;
    private final PaymentMethodRepository paymentMethodRepository;

    public PaymentService(Validator validator, PaymentMethodMapper paymentMethodMapper, PaymentMethodRepository paymentMethodRepository) {
        this.validator = validator;
        this.paymentMethodMapper = paymentMethodMapper;
        this.paymentMethodRepository = paymentMethodRepository;
    }

    public PaymentMethod processReservationPayment(ReservationDTO reservationDTO, User user) {
        PaymentMethod paymentMethod;
        if (Objects.nonNull(reservationDTO.paymentMethod())) {
            paymentMethod = paymentMethodMapper.toEntity(reservationDTO.paymentMethod());
        } else {
            paymentMethod = paymentMethodRepository.findById(reservationDTO.paymentMethodId())
                    .orElseThrow(() -> new PaymentMethodNotFoundException("Payment method with id " + reservationDTO.paymentMethodId() + " was not found in the database"));
        }

        processPayment(paymentMethod);

        if (reservationDTO.saveCreditCard()) {
            paymentMethod.setUser(user);
            paymentMethodRepository.save(paymentMethod);
        }
        return paymentMethod;
    }

    public void processPayment(PaymentMethod paymentMethod) {
        Errors errors = new SimpleErrors(paymentMethod);
        errors.failOnError(PaymentMethodNotFoundException::new);
        validator.validate(paymentMethod, errors);
        errors.hasErrors();
    }

}
