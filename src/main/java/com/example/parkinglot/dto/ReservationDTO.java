package com.example.parkinglot.dto;

import com.example.parkinglot.entity.Reservation;

import java.time.LocalDateTime;

public record ReservationDTO(
        Long userId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Long spotId,
        Long carId,
        CarDTO car,
        Long paymentMethodId,
        PaymentMethodDTO paymentMethod,
        boolean saveCreditCard
) {
    public ReservationDTO(Reservation reservation) {
        this(
                reservation.getUser() != null ? reservation.getUser().getId() : null,
                reservation.getStartTime(),
                reservation.getEndTime(),
                reservation.getSpot() != null ? reservation.getSpot().getId() : null,
                reservation.getCar() != null ? reservation.getCar().getId() : null,
                null,
                reservation.getPaymentMethod() != null ? reservation.getPaymentMethod().getId() : null,
                null,
                false

        );
    }
}
