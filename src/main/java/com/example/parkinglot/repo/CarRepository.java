package com.example.parkinglot.repo;

import com.example.parkinglot.entity.Car;
import com.example.parkinglot.entity.Price;
import com.example.parkinglot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {


    List<Car> findAll();

    List<Car> findByUserFirstName(String firstName);

    Optional<Car> getOneByCarId(Long aLong);

    @Query("SELECT COUNT(c) FROM Car c WHERE c.color = 'Red'")
    long countRedCars();
}




