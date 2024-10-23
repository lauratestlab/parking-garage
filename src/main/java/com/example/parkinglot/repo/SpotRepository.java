package com.example.parkinglot.repo;

import com.example.parkinglot.entity.Spot;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SpotRepository
        extends CrudRepository<Spot, Long> {
    List<Spot> findAll();
    List<Spot> findBySpotName(String spotName);
}




