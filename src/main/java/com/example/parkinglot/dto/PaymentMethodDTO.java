package com.example.parkinglot.dto;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.CreditCardNumber;

public record PaymentMethodDTO(
        Long id,

        @NotNull
        @Size(min = 5, max = 5)
        @Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$",
                message = "Must be formatted MM/YY")
        String expirationDate,

        @NotNull
        @Digits(integer = 3, fraction = 0, message = "Invalid CVV")
        int ccv,

        @NotNull
        @CreditCardNumber(message = "Not a valid credit card number")
        String card_number,

        @NotNull
        @NotBlank(message = "Name is required")
        String fullName,

        @NotNull
        @NotBlank(message = "Street is required")
        String street,

        @NotNull
        @NotBlank(message = "City is required")
        String city,

        @NotNull
        @NotBlank(message = "State is required")
        String state,

        @NotNull
        @NotBlank(message = "Zip code is required")
        String zip,

        @NotNull
        UserDTO user
) {
}
