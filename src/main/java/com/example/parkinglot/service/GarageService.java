package com.example.parkinglot.service;

import com.example.parkinglot.entity.Garage;
import com.example.parkinglot.repo.GarageRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.example.parkinglot.config.Constants.DEFAULT_GARAGE_ID;

@Service
public class GarageService {
    private final GarageRepository garageRepository;

    public GarageService(GarageRepository garageRepository) {
        this.garageRepository = garageRepository;
    }

    public BigDecimal getMemberDiscount() {
        return garageRepository.memberDiscount(DEFAULT_GARAGE_ID).orElseThrow(() -> new RuntimeException("Member discount setting not found"));
    }

    public Garage getDefaultGarage() {
        return garageRepository.findById(DEFAULT_GARAGE_ID).orElseThrow(() -> new RuntimeException("Garage information not found"));
    }

    public Garage save(Garage garage) {
        garage.setId(DEFAULT_GARAGE_ID);
        return garageRepository.save(garage);
    }
}
