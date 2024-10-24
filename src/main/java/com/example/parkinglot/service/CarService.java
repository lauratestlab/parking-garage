package com.example.parkinglot.service;


import com.example.parkinglot.dto.CarDTO;
import com.example.parkinglot.entity.*;
import com.example.parkinglot.repo.*;
import org.springframework.stereotype.Service;

@Service
public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car createCar(CarDTO carDTO) {
        Car car = new Car();
        car.setMake(carDTO.make());
        car.setModel(carDTO.model());
        car.setColor(carDTO.color());
        car.setRegistration(carDTO.registration());

        return carRepository.save(car);
    }

}
