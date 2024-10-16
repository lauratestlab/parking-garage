package com.example.parkinglot.repo;

import com.example.parkinglot.entity.Car;
import com.example.parkinglot.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface CarRepository extends CrudRepository<Car, Long> {
}