package com.example.parkinglot.web.rest;

import com.example.parkinglot.entity.Garage;
import com.example.parkinglot.service.GarageService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/settings")
public class GarageResource {

    private final GarageService garageService;

    public GarageResource(GarageService garageService) {
        this.garageService = garageService;
    }

    @GetMapping
    public Garage getSettings() {
        return garageService.getDefaultGarage();

    }

    @PutMapping
    public Garage getRevenue(@Valid @RequestBody Garage garage) {
        return garageService.save(garage);
    }
}
