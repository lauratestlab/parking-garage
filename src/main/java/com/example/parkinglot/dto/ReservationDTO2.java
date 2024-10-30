package com.example.parkinglot.dto;

import com.example.parkinglot.entity.PaymentMethod;
import com.example.parkinglot.entity.Reservation;
import com.example.parkinglot.enums.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReservationDTO2(
//        Long userId,
//        LocalDateTime startTime,
//        LocalDateTime endTime,
//        Long spotId,
//        Long carId,
//        CarDTO car
        Long reservationId,
        Long userId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Long spotId,
        Long carId,
        BigDecimal price,
        String confirmationCode,
        Status status
)


{
    public ReservationDTO2(Reservation reservation) {
        this(
                reservation.getId(),
                reservation.getUser() != null ? reservation.getUser().getId() : null,
                reservation.getStartTime(),
                reservation.getEndTime(),
                reservation.getSpot() != null ? reservation.getSpot().getId() : null,
                reservation.getCar() != null ? reservation.getCar().getId() : null,
                reservation.getPrice(),
                reservation.getConfirmationCode(),
                reservation.getStatus()

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

//    @Override
//    public CarDTO car() {
//        return car;
//    }

//    @Override
//    public Long paymentMethodId() {
//        return paymentMethodId;
//    }
//
//    @Override
//    public PaymentMethodDTO paymentMethod() {
//        return paymentMethod;
//    }
//
//    @Override
//    public boolean saveCreditCard() {
//        return saveCreditCard;
//    }
}

