package com.example.parkinglot.web.rest;
import com.example.parkinglot.dto.CarDTO;
import com.example.parkinglot.service.CarService;
import com.example.parkinglot.service.CarService.*;
import com.example.parkinglot.entity.Car;
import com.example.parkinglot.entity.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {
    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/red-cars/count")
    public Long getAllRedCars() {

        return carService.getRedCarCount();
    }

    @GetMapping("/all")
    public List<CarDTO> getAllCars() {
        return carService.getAllCars();
    }


}
