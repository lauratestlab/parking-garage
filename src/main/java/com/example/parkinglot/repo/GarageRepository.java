package com.example.parkinglot.repo;

import com.example.parkinglot.entity.Garage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface GarageRepository extends JpaRepository<Garage, Long> {

    Optional<Garage> findById(Long id);

    @Query("SELECT g.memberDiscount FROM Garage g WHERE g.id = :id")
    Optional<BigDecimal> memberDiscount(Long id);
}
