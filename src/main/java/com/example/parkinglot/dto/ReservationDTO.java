package com.example.parkinglot.dto;

import java.time.LocalDateTime;

public record ReservationDTO(
        Long userId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Long spotId,
        Long carId,
        Long paymentMethodId
) {
}
