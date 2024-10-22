package com.example.parkinglot.service;

import com.example.parkinglot.repo.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public long getRedCarCount() {
        return carRepository.countRedCars();
    }
}
