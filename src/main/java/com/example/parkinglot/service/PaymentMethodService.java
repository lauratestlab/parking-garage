package com.example.parkinglot.service;

import com.example.parkinglot.dto.PaymentMethodDTO;

import java.util.List;

public interface PaymentMethodService {

    List<PaymentMethodDTO> getAllPaymentMethods();

    PaymentMethodDTO getPaymentMethod(Long paymentMethodId);

    PaymentMethodDTO createPaymentMethod(PaymentMethodDTO card_number);

}