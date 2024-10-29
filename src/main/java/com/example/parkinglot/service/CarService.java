package com.example.parkinglot.service;


import com.example.parkinglot.dto.CarDTO;
import com.example.parkinglot.entity.*;
import com.example.parkinglot.repo.*;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public long getRedCarCount() {
        return carRepository.countRedCars();
    }

//    public List<Car> getAllCars() {
//        return carRepository.findAll();
//    }

    public List<CarDTO> getAllCars() {
        List<Car> cars = carRepository.findAll();
        return cars.stream()
                .map(car -> new CarDTO(car.getModel(), car.getMake(), car.getColor(), car.getRegistration()))
                .toList();
    }



}
