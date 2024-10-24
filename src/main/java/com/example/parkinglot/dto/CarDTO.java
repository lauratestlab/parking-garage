package com.example.parkinglot.dto;

public record CarDTO(
        String model,
        String make,
        String color,
        String registration
) {}