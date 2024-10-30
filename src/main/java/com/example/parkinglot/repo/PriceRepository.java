package com.example.parkinglot.repo;

import com.example.parkinglot.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PriceRepository
        extends JpaRepository<Price, Integer> {
    List<Price> findAll();

    Optional<Price> findByDuration(int duration);
}
