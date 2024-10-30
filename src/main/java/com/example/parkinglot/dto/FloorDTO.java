package com.example.parkinglot.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record FloorDTO(
        Long id,

        @NotNull
        @Size(min = 1, max = 20)
        String name
) {

}