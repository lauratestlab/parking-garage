package com.example.parkinglot.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ReservationStartDTO(
        @NotNull
        @Size(min = 1, max = 25)
        String model,

        @NotNull
        @Size(min = 1, max = 25)
        String make,

        @NotNull
        @Size(min = 1, max = 25)
        String color,

        @NotNull
        @Size(min = 1, max = 10)
        String registration,

        @Email
        @Size(min = 5, max = 254)
        String email
) {
}
