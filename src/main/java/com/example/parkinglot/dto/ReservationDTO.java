package com.example.parkinglot.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReservationDTO(
        Long userId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Long spotId,
        Long carId,
        CarDTO car,
        boolean saveCar,
        Long paymentMethodId,
        BigDecimal price,
        PaymentMethodDTO paymentMethod,
        boolean saveCreditCard,
        @Email
        @Size(min = 5, max = 254)
        String email
) {}
