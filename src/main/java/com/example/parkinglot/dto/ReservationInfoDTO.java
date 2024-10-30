package com.example.parkinglot.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReservationInfoDTO(
        boolean success,
        boolean overstay,
        String message,
        String confirmationCode,
        Long userId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Long spotId,
        Long carId,
        BigDecimal price
) {
}
