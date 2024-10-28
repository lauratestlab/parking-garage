package com.example.parkinglot.dto;

public record PaymentMethodDTO (
    String expirationDate,
    int ccv,
    String fullName,
    String deliveryStreet,
    String deliveryCity,
    String deliveryState,
    String deliveryZip,
    int card_number,
    Long paymentMethodId

){}


