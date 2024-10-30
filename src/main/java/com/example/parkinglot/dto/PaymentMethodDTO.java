package com.example.parkinglot.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.CreditCardNumber;

public record PaymentMethodDTO(
        Long paymentMethodId,

        @Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$",
                message = "Must be formatted MM/YY")
        String expirationDate,

        @Digits(integer = 3, fraction = 0, message = "Invalid CVV")
        int ccv,

        @CreditCardNumber(message = "Not a valid credit card number")
        String card_number,

        String fullName,

        @NotBlank(message = "Street is required")
        String deliveryStreet,

        @NotBlank(message = "City is required")
        String deliveryCity,

        @NotBlank(message = "State is required")
        String deliveryState,

        @NotBlank(message = "Zip code is required")
        String deliveryZip
) {
}
