package com.example.parkinglot.dto;

import com.example.parkinglot.entity.PaymentMethod;
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
)


{
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



    @Override
    public Long userId() {
        return userId;
    }

    @Override
    public LocalDateTime startTime() {
        return startTime;
    }

    @Override
    public LocalDateTime endTime() {
        return endTime;
    }

    @Override
    public Long spotId() {
        return spotId;
    }

    @Override
    public Long carId() {
        return carId;
    }

    @Override
    public CarDTO car() {
        return car;
    }

    @Override
    public Long paymentMethodId() {
        return paymentMethodId;
    }

    @Override
    public PaymentMethodDTO paymentMethod() {
        return paymentMethod;
    }

    @Override
    public boolean saveCreditCard() {
        return saveCreditCard;
    }
}
